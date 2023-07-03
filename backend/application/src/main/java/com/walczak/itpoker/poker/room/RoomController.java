package com.walczak.itpoker.poker.room;

import com.walczak.api.dto.*;
import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.PlayerRole;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final RoomResultStage roomResultStage;

    public RoomController(RoomService roomService, RoomResultStage roomResultStage) {
        this.roomService = roomService;
        this.roomResultStage = roomResultStage;
    }

    @PostMapping
    public NewRoomResponseDTO createRoom(@RequestBody NewRoomDTO dto) {
        Room savedRoom = roomService.saveNewRoom(mapToNewRoom(dto));
        String newPlayerId = savedRoom.getPlayers().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player was not saved properly"))
                .getId().toString();
        return new NewRoomResponseDTO(savedRoom.getId().toString(), newPlayerId);
    }

    @GetMapping("/{id}")
    public RoomInfoDTO getRoomInfo(@PathVariable("id") String id) {
        Room room = roomService.getRoom(id);
        return RoomInfoDTO.builder()
                .id(id)
                .units(room.getUnits())
                .cardsValues(room.getCardsValues())
                .name(room.getName())
                .players(mapToPlayers(room))
                .build();
    }

    public void detachPlayer(String playerId, String roomID) { //todo preRemove
        Room room = roomService.getRoom(roomID);
        List<Player> players = room.getPlayers().stream()
                .filter(p -> !p.getId().toString().equals(playerId)).toList();
        Room updatedRoom = room.toBuilder()
                .players(players).build();
        roomService.saveNewRoom(updatedRoom);
    }

    private static List<PlayerInfoDTO> mapToPlayers(Room room) {
        return room.getPlayers().stream()
                .map(RoomController::mapToPlayerInfoDTO).toList();
    }

    private static PlayerInfoDTO mapToPlayerInfoDTO(Player a) {
        return PlayerInfoDTO.builder()
                .id(a.getId().toString())
                .isObsoleted(a.isObsoleted())
                .selectedCard(a.getSelectedCard())
                .isObserver(a.isObserver())
                .name(a.getName())
                .role(a.getRole().toString())
                .isAdmin(a.isAdmin())
                .selectedUnit(a.getSelectedUnit())
                .avatarIdx(a.getAvatarIdx())
                .build();
    }

    @PostMapping("/add")
    public void addPlayer(@RequestBody AddPlayerDTO dto) {
        Room room = roomService.getRoom(dto.getRoomId());
        Player newPlayer = mapToPlayer(dto.getPlayer(), false);
        List<Player> playerList = room.getPlayers();
        playerList.add(newPlayer);
        Room roomToUpdate = room.toBuilder()
                .players(playerList).build();
        roomService.saveNewRoom(roomToUpdate);
    }

    @GetMapping("/result/{id}")
    public List<ResultDTO> getResult(@PathVariable("id") String roomId) {
        if (!roomResultStage.hasResult(roomId)) {
            Map<PlayerRole, Double> roomResult = roomService.calculateRoomResult(roomId);
            roomResultStage.setRoomInResultStage(roomId, roomResult);
        }
        return roomResultStage.getResult(roomId).entrySet().stream()
                .map(entry -> new ResultDTO(entry.getKey().toString(), entry.getValue(), "h"))
                .toList();
    }

    @PostMapping("/result/reset/{id}")
    public void resetResult(@PathVariable("id") String roomId) {
        roomResultStage.removeRoomFromResultStage(roomId);
        Room room = roomService.getRoom(roomId);
        List<Player> playersWithClearedVotes = room.getPlayers().stream()
                .map(Player::cleanVote)
                .collect(Collectors.toList());
        room.toBuilder()
                .players(playersWithClearedVotes)
                .build();
        roomService.saveNewRoom(room);

    }

    private static Room mapToNewRoom(NewRoomDTO newRoomDTO) {
        Player player = mapToPlayer(newRoomDTO.getRoomCreator(), true);
        return Room.builder()
                .name(newRoomDTO.getName())
                .cardsValues(newRoomDTO.getCardsValues())
                .units(newRoomDTO.getUnits())
                .players(Collections.singletonList(player))
                .build();
    }

    private static Player mapToPlayer(NewPlayerDTO roomCreator, boolean isAdmin) {
        return Player.builder()
                .name(roomCreator.getName())
                .role(PlayerRole.valueOf(roomCreator.getRole()))
                .avatarIdx(roomCreator.getAvatarIdx())
                .isAdmin(isAdmin)
                .build();
    }
}
