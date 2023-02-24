package com.walczak.itpoker.domain.room;

import com.walczak.itpoker.domain.common.Mapper;
import com.walczak.itpoker.domain.participant.Player;
import com.walczak.itpoker.dto.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/room")
public class RoomRestController {

    private RoomService roomService;

    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/exists/{id}")
    public boolean checkIfRoomExists(@PathVariable("id") String id) {
        return roomService.getById(id).isPresent();
    }

    @GetMapping("/{id}")
    public RoomDTO getRoomData(@PathVariable("id") String id) {
        Room room = roomService.getById(id).orElseThrow(() -> new IllegalStateException("Room doesn't exist"));
        return Mapper.mapToRoomDTO(room);
    }

    @PostMapping("/create")
    public NewRoomResponseDTO createRoom(@RequestBody NewRoomDTO newRoomDTO) {
        String roomId = UUID.randomUUID().toString();
        String playerId = UUID.randomUUID().toString();
        Player newPlayer = Mapper.mapToFounderPlayer(newRoomDTO.roomFounder(), playerId);
        Room newRoom = Mapper.mapToRoom(newRoomDTO, roomId, newPlayer);
        roomService.createNewRoom(newRoom);
        return new NewRoomResponseDTO(roomId, newRoom.getName(), playerId);
    }

    @PostMapping("/join/{id}")
    public RoomJoinResponseDTO join(@RequestBody NewParticipantDTO newParticipant, @PathVariable("id") String roomId) {
        String participantId = UUID.randomUUID().toString();
        Player newPlayer = Mapper.mapToParticipantData(newParticipant, participantId);
        Optional<Room> updatedRoom = roomService.addParticipant(newPlayer, roomId);
        return updatedRoom
                .map(Mapper::mapToRoomDTO)
                .map(data -> new RoomJoinResponseDTO(participantId, data, null))
                .orElse(new RoomJoinResponseDTO(null, null, "This room probably doesn't exist"));
    }


}
