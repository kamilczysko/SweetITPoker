package com.walczak.itpoker.dto;

public record RoomJoinDTO(
        String roomId,
        NewParticipantDTO newParticipant
) {
}