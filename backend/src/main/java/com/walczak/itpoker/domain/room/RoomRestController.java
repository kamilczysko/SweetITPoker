package com.walczak.itpoker.domain.room;

import com.walczak.itpoker.domain.common.Mapper;
import com.walczak.itpoker.domain.participant.Player;
import com.walczak.itpoker.dto.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/room")
public class RoomRestController {

    private RoomService roomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RoomRestController(RoomService roomService, SimpMessagingTemplate simpMessagingTemplate) {
        this.roomService = roomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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
    public RoomJoinResponseDTO join(@RequestBody NewPlayerDTO playerToAdd, @PathVariable("id") String roomId) {
        String playerId = UUID.randomUUID().toString();
        Player newPlayer = Mapper.mapToPlayerData(playerToAdd, playerId);
        Optional<Room> updatedRoom = roomService.addPlayer(newPlayer, roomId);
        Room existingRoom = roomService.getExistingById(roomId);
        simpMessagingTemplate.convertAndSend("/topic/room/" + roomId, Mapper.mapToRoomDTO(existingRoom));
        return updatedRoom
                .map(Mapper::mapToRoomDTO)
                .map(data -> new RoomJoinResponseDTO(playerId, data.roomId(), null))
                .orElse(new RoomJoinResponseDTO(null, null, "This room probably doesn't exist"));
    }
}
