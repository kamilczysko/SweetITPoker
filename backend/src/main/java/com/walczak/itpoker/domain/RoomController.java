package com.walczak.itpoker.domain;

import com.walczak.itpoker.domain.common.Mapper;
import com.walczak.itpoker.domain.participant.Player;
import com.walczak.itpoker.domain.participant.PlayerService;
import com.walczak.itpoker.domain.room.Room;
import com.walczak.itpoker.domain.room.RoomService;
import com.walczak.itpoker.dto.ParticipantModificationDTO;
import com.walczak.itpoker.dto.RoomDTO;
import com.walczak.itpoker.dto.RoomLeaveDTO;
import com.walczak.itpoker.dto.RoomModificationDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
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
        playerService.removeParticipant(roomLeaveDTO.participantId());
        Room room = roomService.getExistingById(roomLeaveDTO.roomId());
        if(room.getPlayers().isEmpty()) {
            System.out.println("remove room: "+room.getId());
            roomService.deleteRoom(room.getId());
            return;
        }
        Mapper.mapToRoomDTO(room);
        simpMessagingTemplate.convertAndSend("/topic/leave/" + roomLeaveDTO.roomId(), roomLeaveDTO.participantId());
    }

    @MessageMapping("/room-modification")
    public void modifyRoom(@Payload RoomModificationDTO dto) {
        Room foundRoom = roomService.getExistingById(dto.roomId());
        Room modifiedRoom = Room.builder(foundRoom)
                .build();
        RoomDTO roomState = Mapper.mapToRoomDTO(roomService.updateRoom(modifiedRoom));
        simpMessagingTemplate.convertAndSend("/topic/room-modification/" + dto.roomId(), roomState);
    }

    @MessageMapping("/room-state")
    public void updateRoomState(@Payload ParticipantModificationDTO dto) {
        if(Optional.ofNullable(dto).isEmpty()) {
            return;
        }
        if(dto.resetAllVotes()) {
            clearVotesForAllParticipants(dto.roomId());
        } else if (!isVotingFinished(dto.roomId())) {
            playerService.updateParticipant(dto.modifiedParticipant());
        }
        Optional<Room> roomState = roomService.getById(dto.roomId());
        roomState.map(Mapper::mapToRoomDTO)
                        .ifPresent(state -> simpMessagingTemplate.convertAndSend("/topic/room-state/" + dto.roomId(), state));
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
        playerService.updateAllParticipants(participantsWithClearedVotes);
    }
}