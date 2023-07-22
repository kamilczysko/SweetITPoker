package com.walczak.itpoker.poker;

import com.walczak.api.dto.CardSelectionDTO;
import com.walczak.api.dto.PlayerInfoDTO;
import com.walczak.api.dto.ResultDTO;
import com.walczak.api.dto.RoomInfoDTO;
import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.PlayerController;
import com.walczak.itpoker.poker.room.RoomController;
import com.walczak.itpoker.poker.room.RoomResultStage;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class GameWSController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final RoomController roomController;
    private final PlayerController playerController;
    private final RoomResultStage roomResultStage;

    public GameWSController(SimpMessagingTemplate simpMessagingTemplate, RoomController roomController, PlayerController playerController, RoomResultStage roomResultStage) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.roomController = roomController;
        this.playerController = playerController;
        this.roomResultStage = roomResultStage;
    }
    @EventListener
    void notifyRoomStateChange( RoomStateChangeEvent event) {
        String roomId = event.getRoomId();
        notifyAboutRoomStateChange(roomId);
        if(event.shouldResetRoom()) {
            simpMessagingTemplate.convertAndSend("/topic/room/reset/" + roomId, roomId);
        }
    }

    private void notifyAboutRoomStateChange(String roomId) {
        List<PlayerInfoDTO> players = roomController.getRoomInfo(roomId).getPlayers();
        simpMessagingTemplate.convertAndSend("/topic/room/" + roomId, players);
        notifyAboutResult(players, roomId);
    }
    private void notifyAboutResult(List<PlayerInfoDTO> players, String roomId) {
        List<PlayerInfoDTO> nonObservers = players.stream().filter(player -> !player.isObserver()).toList();
        boolean allPlayersVoted = nonObservers.stream()
                .noneMatch(player -> Strings.isBlank(player.getSelectedCard()));
        if (allPlayersVoted && !roomResultStage.hasResult(roomId)) {
            List<ResultDTO> result = roomController.getResult(roomId);
            simpMessagingTemplate.convertAndSend("/topic/room/result/" + roomId, result);
        }
    }
}