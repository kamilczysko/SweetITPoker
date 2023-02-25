package com.walczak.itpoker.dto;

public record RoomJoinDTO(
        String roomId,
        NewPlayerDTO newParticipant
) {
}
