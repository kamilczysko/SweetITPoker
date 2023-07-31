package com.walczak.itpoker.poker;

import com.walczak.api.dto.CardSelectionDTO;
import com.walczak.api.dto.PlayerInfoDTO;
import com.walczak.api.dto.RoomInfoDTO;
import com.walczak.itpoker.poker.player.PlayerController;
import com.walczak.itpoker.poker.room.RoomController;
import com.walczak.itpoker.poker.room.RoomResultStage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameRestController {

    private final RoomController roomController;
    private final PlayerController playerController;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RoomResultStage roomResultStage;

    public GameRestController(RoomController roomController, PlayerController playerController, ApplicationEventPublisher applicationEventPublisher, RoomResultStage roomResultStage) {
        this.roomController = roomController;
        this.playerController = playerController;
        this.applicationEventPublisher = applicationEventPublisher;
        this.roomResultStage = roomResultStage;
    }

    @DeleteMapping("/delete/{playerId}")
    public void deletePlayer(@PathVariable("playerId") String playerId) {
        String roomId = playerController.getPlayersRoomId(playerId);
        publishMessage(roomId, playerId, "has left room");
        roomController.detachPlayer(playerId, roomId);
        setAdminIfNonLeftInTheRoom(roomId);
        applicationEventPublisher.publishEvent(new RoomStateChangeEvent(roomId));
    }

    private void setAdminIfNonLeftInTheRoom(String roomId) {
        RoomInfoDTO roomInfo = roomController.getRoomInfo(roomId);
        if (roomInfo.getPlayers().stream().noneMatch(PlayerInfoDTO::isAdmin)) {
            roomInfo.getPlayers().stream().findFirst()
                    .ifPresent(p -> {
                        publishMessage(roomId, p.getId(), "is now new admin");
                        playerController.toggleAdmin(p.getId());
                    });
        }
    }

    @PostMapping("/reset/{id}")
    public void reset(@PathVariable("id") String roomId) {
        RoomInfoDTO roomInfo = roomController.getRoomInfo(roomId);
        List<String> players = roomInfo.getPlayers().stream().map(PlayerInfoDTO::getId).toList();
        playerController.resetAllPlayers(players);
        roomResultStage.removeRoomFromResultStage(roomId);
        applicationEventPublisher.publishEvent(new RoomStateChangeEvent(roomId, true));
    }

    @PostMapping("/vote")
    public void vote(@RequestBody CardSelectionDTO dto) {
        String playersRoomId = playerController.getPlayersRoomId(dto.getPlayerId());
        publishMessage(playersRoomId, dto.getPlayerId(), "has voted");
        if (roomResultStage.hasResult(playersRoomId)) {
            return;
        }
        PlayerInfoDTO updatedPlayer = playerController.updatePlayerCard(dto);
        String roomId = updatedPlayer.getRoomId();
        applicationEventPublisher.publishEvent(new RoomStateChangeEvent(roomId, false));
    }

    private void publishMessage(String roomId, String playerId, String message) {
        applicationEventPublisher.publishEvent(RoomMessageEvent.Builder.builder()
                .roomId(roomId)
                .playerId(playerId)
                .message(message)
                .build());
    }
}
