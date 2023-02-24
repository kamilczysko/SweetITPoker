package com.walczak.itpoker.domain.common;

import com.walczak.itpoker.domain.participant.Player;
import com.walczak.itpoker.domain.participant.SelectedCard;
import com.walczak.itpoker.domain.room.Room;
import com.walczak.itpoker.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public static Player mapToParticipant(PlayerDTO playerDTO) {
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
                .participants(List.of(founderOfRoom))
                .build();
    }

    public static Player mapToFounderPlayer(NewParticipantDTO roomFounderDTO, String participantId) {
        return Player.Builder.builder()
                .avatarIdx(roomFounderDTO.avatarIdx())
                .role(roomFounderDTO.role())
                .isAdmin(true)
                .isObserver(false)
                .id(participantId)
                .name(roomFounderDTO.name())
                .build();
    }

    public static RoomDTO mapToRoomDTO(Room data) {
        return new RoomDTO(data.getId(),
                data.getName(),
                mapToParticipantsDTO(data.getPlayers()));
    }

    public static List<PlayerDTO> mapToParticipantsDTO(List<Player> playerData) {
        return playerData.stream()
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

    public static Player mapToParticipantData(NewParticipantDTO newParticipant, String participantId) {
        return Player.Builder.builder()
                .avatarIdx(newParticipant.avatarIdx())
                .role(newParticipant.role())
                .isAdmin(false)
                .isObserver(false)
                .id(participantId)
                .name(newParticipant.name())
                .build();
    }
    public static Player getParticipantWithClearVote(Player data) {
        return Player.builder(data)
                .selectedCard(null)
                .build();
    }
}
