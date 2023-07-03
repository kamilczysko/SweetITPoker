package com.walczak.itpoker.poker;

import com.walczak.api.dto.PlayerInfoDTO;
import com.walczak.api.dto.RoomInfoDTO;
import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.PlayerController;
import com.walczak.itpoker.poker.room.RoomController;
import com.walczak.itpoker.poker.room.RoomResultStage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        roomController.detachPlayer(playerId, roomId);
        applicationEventPublisher.publishEvent(new RoomStateChangeEvent(roomId));
    }

    @PostMapping("/reset/{id}")
    public void reset(@PathVariable("id") String roomId) {
        RoomInfoDTO roomInfo = roomController.getRoomInfo(roomId);
        List<String> players = roomInfo.getPlayers().stream().map(PlayerInfoDTO::getId).toList();
        playerController.resetAllPlayers(players);
        roomResultStage.removeRoomFromResultStage(roomId);
        applicationEventPublisher.publishEvent(new RoomStateChangeEvent(roomId, true));
    }
}
