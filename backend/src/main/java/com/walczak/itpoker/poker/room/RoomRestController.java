package com.walczak.itpoker.poker.room;

import com.walczak.itpoker.infrastructure.captcha.CaptchaService;
import com.walczak.itpoker.poker.common.Mapper;
import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.PlayerService;
import com.walczak.itpoker.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/room")
public class RoomRestController {
    
    private final RoomService roomService;
    private final PlayerService playerService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final CaptchaService captchaService;

    public RoomRestController(RoomService roomService, PlayerService playerService, SimpMessagingTemplate simpMessagingTemplate, CaptchaService captchaService) {
        this.roomService = roomService;
        this.playerService = playerService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.captchaService = captchaService;
    }

    @GetMapping("/{id}")
    public RoomDTO getRoomData(@PathVariable("id") String id) {
        Room room = roomService.getById(id).orElseThrow(() -> new IllegalStateException("Room doesn't exist"));
        return Mapper.mapToRoomDTO(room);
    }

    @PostMapping("/create")
    public NewRoomResponseDTO createRoom(HttpServletRequest request, @RequestBody NewRoomDTO newRoomDTO) {
        captchaService.processResponse(newRoomDTO.token(), request.getRemoteAddr());
        String roomId = UUID.randomUUID().toString();
        String playerId = UUID.randomUUID().toString();
        Player newPlayer = Mapper.mapToFounderPlayer(newRoomDTO.roomFounder(), playerId);
        Room newRoom = Mapper.mapToRoom(newRoomDTO, roomId, newPlayer);
        roomService.createNewRoom(newRoom);
        return new NewRoomResponseDTO(roomId, newRoom.getName(), playerId);
    }

    @PostMapping("/join/{id}")
    public RoomJoinResponseDTO join(HttpServletRequest request, @RequestBody NewPlayerDTO playerToAdd, @PathVariable("id") String roomId) {
        captchaService.processResponse(playerToAdd.token(), request.getRemoteAddr());

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

    @PostMapping("/{room-id}/modify-player")
    public void updatePlayer(@RequestBody PlayerDTO player, @PathVariable("room-id") String roomId) {
        playerService.updatePlayer(player);
        Room existingRoom = roomService.getExistingById(roomId);
        simpMessagingTemplate.convertAndSend("/topic/room/" + roomId, Mapper.mapToRoomDTO(existingRoom));
    }
}
