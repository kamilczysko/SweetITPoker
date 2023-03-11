package com.walczak.itpoker.domain;

import com.walczak.itpoker.domain.common.Mapper;
import com.walczak.itpoker.domain.player.Player;
import com.walczak.itpoker.domain.player.PlayerService;
import com.walczak.itpoker.domain.room.Room;
import com.walczak.itpoker.domain.room.RoomService;
import com.walczak.itpoker.dto.PlayerActionDTO;
import com.walczak.itpoker.dto.ResultDTO;
import com.walczak.itpoker.dto.RoomLeaveDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RoomController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RoomService roomService;
    private final PlayerService playerService;

    public RoomController(RoomService roomService, PlayerService playerService, SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.roomService = roomService;
        this.playerService = playerService;
    }

    @MessageMapping("/leave")
    public void leave(@Payload RoomLeaveDTO roomLeaveDTO) {

        removeUser(roomLeaveDTO);

        Room room = roomService.getExistingById(roomLeaveDTO.roomId());
        setAdminIfNotPresent(room);
        if (room.getPlayers().stream().allMatch(Player::isObsoleted)) {
            System.out.println("remove room: " + room.getId());
            roomService.deleteRoom(room.getId());
            return;
        }
        Optional<Room> roomState = roomService.getById(roomLeaveDTO.roomId());
        roomState.map(Mapper::mapToRoomDTO)
                .ifPresent(state -> simpMessagingTemplate.convertAndSend("/topic/room/" + roomLeaveDTO.roomId(), state));
    }

    private void setAdminIfNotPresent(Room room) {
        boolean isAnyAdminInRoom = room.getPlayers().stream().anyMatch(Player::isAdmin);
        if(!isAnyAdminInRoom) {
            System.out.println("change admin for room: "+room.getId());
            room.getPlayers().stream()
                    .filter(player -> !player.isObsoleted())
                    .min(Comparator.comparing(Player::isObserver))
                    .map(player -> Player.builder(player).isAdmin(true).build())
                    .map(Mapper::mapToParticipantDTO)
                    .ifPresent(playerService::updatePlayer);
        }
    }

    private void removeUser(RoomLeaveDTO roomLeaveDTO) {
        if (isVotingFinished(roomLeaveDTO.roomId())) {
            playerService.obsoletePlayer(roomLeaveDTO.playerId());
        } else {
            playerService.removePlayer(roomLeaveDTO.playerId());
        }
    }

    @MessageMapping("/room")
    public void updateRoomState(@Payload PlayerActionDTO dto) {
        if (Optional.ofNullable(dto).isEmpty()) {
            return;
        }
        if (dto.resetAllVotes()) {
            clearVotesForAllParticipants(dto.roomId());
        } else if (!isVotingFinished(dto.roomId())) {
            playerService.updatePlayer(dto.modifiedPlayer());
        }

        if (isVotingFinished(dto.roomId())) {
            List<ResultDTO> result = getResult(dto.roomId());
            simpMessagingTemplate.convertAndSend("/topic/room/result/" + dto.roomId(), result);
        }

        Optional<Room> roomState = roomService.getById(dto.roomId());
        roomState.map(Mapper::mapToRoomDTO)
                .ifPresent(state -> simpMessagingTemplate.convertAndSend("/topic/room/" + dto.roomId(), state));
    }

    private List<ResultDTO> getResult(String roomId) {
        Map<String, Double> stringDoubleMap = roomService.calculateResult(roomId);
        return stringDoubleMap.entrySet().stream()
                .map(entry -> new ResultDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private boolean isVotingFinished(String roomId) {
        Optional<Room> roomState = roomService.getById(roomId);
        Boolean allNoObserversHasVoted = roomState.map(room -> room.getPlayers().stream()
                .filter(participant -> !participant.isObserver())
                .allMatch(Player::hasVoted)).orElse(false);
        Boolean notEverybodyIsObserver = roomState.map(room -> room.getPlayers().stream()
                .anyMatch(participant -> !participant.isObserver())).orElse(true);
        return allNoObserversHasVoted && notEverybodyIsObserver;
    }

    private void clearVotesForAllParticipants(String roomId) {
        List<Player> participantsWithClearedVotes = roomService.getExistingById(roomId).getPlayers()
                .stream()
                .map(Mapper::getParticipantWithClearVote)
                .collect(Collectors.toList());
        playerService.updateAllPlayers(participantsWithClearedVotes);
    }
}