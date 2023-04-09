package com.walczak.itpoker.dto;

public record NewRoomDTO(
        String token,
        String roomName,
        NewPlayerDTO roomFounder
) { }
