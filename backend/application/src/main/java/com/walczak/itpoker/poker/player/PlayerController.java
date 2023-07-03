package com.walczak.itpoker.poker.player;

import com.walczak.api.dto.*;
import com.walczak.itpoker.infrastructure.captcha.CaptchaService;
import com.walczak.itpoker.poker.RoomStateChangeEvent;
import com.walczak.itpoker.poker.room.Room;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CaptchaService captchaService;
    public PlayerController(PlayerService playerService, ApplicationEventPublisher applicationEventPublisher, CaptchaService captchaService) {
        this.playerService = playerService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.captchaService = captchaService;
    }

    @PostMapping
    public NewPlayerResponseDTO saveNewPlayer(HttpServletRequest request, @RequestBody AddPlayerDTO dto) {
        captchaService.processResponse(dto.getToken(), request.getRemoteAddr());
        Player savedPlayer = playerService.savePlayer(mapToNewPlayer(dto));
        String playerId = savedPlayer.getId().toString();
        notifyRoomChange(savedPlayer.getRoom().getId());
        return new NewPlayerResponseDTO(playerId);
    }


    @PostMapping("/update/avatar/{playerId}/{avatarIdx}")
    public void updatePlayerAvatar(@PathVariable("playerId") String playerId, @PathVariable("avatarIdx") short avatarIdx) {
        Player playerToUpdate = playerService.getPlayerById(playerId).toBuilder()
                .avatarIdx(avatarIdx).build();
        playerService.updatePlayer(playerToUpdate);
    }

    @PostMapping("/update/card")
    public PlayerInfoDTO updatePlayerCard(@RequestBody CardSelectionDTO dto) {
        Player player = playerService.getPlayerById(dto.getPlayerId());
        Player playerToUpdate = player.toBuilder()
                .selectedCard(dto.getCardValue()).selectedUnit(dto.getUnit()).build();
        playerService.savePlayer(playerToUpdate);
        return mapToPlayerInfo(playerToUpdate);
    }
    private void notifyRoomChange(UUID id) {
        applicationEventPublisher.publishEvent(new RoomStateChangeEvent(id.toString()));
    }
    public String getPlayersRoomId(String playerId) {
        return playerService.getPlayerById(playerId).getRoom().getId().toString();
    }
    @PostMapping("/update/role/{playerId}/{role}")
    public void updatePlayerRole(@PathVariable("playerId") String playerId, @PathVariable("role") String role) {
        Player playerToUpdate = playerService.getPlayerById(playerId).toBuilder()
                .role(PlayerRole.valueOf(role)).build();
        playerService.updatePlayer(playerToUpdate);
    }

    @PostMapping("/update/toggle/observer/{id}")
    public void toggleObserver(@PathVariable("id") String id) {
        Player playerById = playerService.getPlayerById(id);
        if(playerById.getRole().equals(PlayerRole.OBSERVER)) {
            return;
        }
        Player modifiedPlayer = playerById.toBuilder()
                .isObserver(!playerById.isObserver())
                .build();
        playerService.savePlayer(modifiedPlayer);
        notifyRoomChange(playerById.getRoom().getId());
    }

    @PostMapping("/update/toggle/admin/{id}")
    public void toggleAdmin(@PathVariable("id") String id) {
        Player playerById = playerService.getPlayerById(id);
        Player modifiedPlayer = playerById.toBuilder()
                .isAdmin(!playerById.isAdmin())
                .build();
        playerService.savePlayer(modifiedPlayer);
        notifyRoomChange(playerById.getRoom().getId());
    }
    private static PlayerInfoDTO mapToPlayerInfo(Player player) {
        return PlayerInfoDTO.builder()
                .id(player.getId().toString())
                .name(player.getName())
                .role(player.getRole().toString())
                .isAdmin(player.isAdmin())
                .isObserver(player.isObserver())
                .avatarIdx(player.getAvatarIdx())
                .isObsoleted(player.isObsoleted())
                .selectedCard(player.getSelectedCard())
                .selectedUnit(player.getSelectedUnit())
                .roomId(player.getRoom().getId().toString())
                .build();
    }

    private static Player mapToNewPlayer(AddPlayerDTO dto) {
        Room room = Room.builder().id(UUID.fromString(dto.getRoomId())).build();
        Player newPlayer = mapToPlayer(dto.getPlayer());
        return newPlayer.toBuilder().room(room).build();
    }

    private static Player mapToPlayer(NewPlayerDTO playerDTO) {
        boolean observer = PlayerRole.valueOf(playerDTO.getRole()).equals(PlayerRole.OBSERVER);
        return Player.builder()
                .name(playerDTO.getName())
                .role(PlayerRole.valueOf(playerDTO.getRole()))
                .avatarIdx(playerDTO.getAvatarIdx())
                .isObserver(observer)
                .isAdmin(false)
                .build();
    }

    public void resetAllPlayers(List<String> players) {
        playerService.resetAllPlayers(players);
    }
}
