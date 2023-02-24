package com.walczak.itpoker.dto;

public record NewRoomDTO(
        String roomName,
        NewParticipantDTO roomFounder
) { }
