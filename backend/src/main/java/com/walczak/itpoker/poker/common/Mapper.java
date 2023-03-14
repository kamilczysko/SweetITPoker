package com.walczak.itpoker.poker.common;

import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.SelectedCard;
import com.walczak.itpoker.poker.room.Room;
import com.walczak.itpoker.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static final String OBSERVER_CHAN_ROLE = "Observer-chan";

    public static Player mapToPlayer(PlayerDTO playerDTO) {
        SelectedCard selectedCard = null ;
        if(playerDTO.selectedCard() != null) {
            selectedCard = new SelectedCard(playerDTO.selectedCard().cardId(), playerDTO.selectedCard().unit(), playerDTO.selectedCard().value());
        }
        return Player.Builder.builder()
                .selectedCard(selectedCard)
                .avatarIdx(playerDTO.avatarIdx())
                .isAdmin(playerDTO.isAdmin())
                .isObserver(playerDTO.isObserver())
                .id(playerDTO.id())
                .name(playerDTO.name())
                .role(playerDTO.role())
                .build();
    }

    public static Room mapToRoom(NewRoomDTO newRoomDTO, String roomId, Player founderOfRoom) {
        return Room.Builder.builder()
                .name(newRoomDTO.roomName())
                .id(roomId)
                .players(List.of(founderOfRoom))
                .build();
    }

    public static Player mapToFounderPlayer(NewPlayerDTO roomFounderDTO, String participantId) {
        return Player.Builder.builder()
                .avatarIdx(roomFounderDTO.avatarIdx())
                .role(roomFounderDTO.role())
                .isAdmin(true)
                .isObserver(isObserver(roomFounderDTO.role()))
                .id(participantId)
                .name(roomFounderDTO.name())
                .build();
    }

    public static RoomDTO mapToRoomDTO(Room data) {
        return new RoomDTO(data.getId(),
                data.getName(),
                mapToPlayersDTO(data.getPlayers()));
    }

    public static List<PlayerDTO> mapToPlayersDTO(List<Player> playerData) {
        return playerData.stream()
                .filter(player -> !player.isObsoleted())
                .map(Mapper::mapToParticipantDTO)
                .collect(Collectors.toList());
    }

    public static PlayerDTO mapToParticipantDTO(Player player) {
        if(player.hasVoted()) {
            SelectedCardDTO selectedCardDTO = new SelectedCardDTO(player.getSelectedCard().cardId(), player.getSelectedCard().unit(), player.getSelectedCard().value());
            return new PlayerDTO(player.getId(), player.getName(), player.getAvatarIdx(), player.isAdmin(), player.isObserver(), selectedCardDTO, player.getRole());
        }
        return new PlayerDTO(player.getId(), player.getName(), player.getAvatarIdx(), player.isAdmin(), player.isObserver(), null, player.getRole());
    }

    public static Player mapToPlayerData(NewPlayerDTO newParticipant, String participantId) {
        return Player.Builder.builder()
                .avatarIdx(newParticipant.avatarIdx())
                .role(newParticipant.role())
                .isAdmin(false)
                .isObserver(isObserver(newParticipant.role()))
                .id(participantId)
                .name(newParticipant.name())
                .build();
    }

    private static boolean isObserver(String role) {
        return role.equals(OBSERVER_CHAN_ROLE);
    }

    public static Player getParticipantWithClearVote(Player data) {
        return Player.builder(data)
                .selectedCard(null)
                .build();
    }
}
