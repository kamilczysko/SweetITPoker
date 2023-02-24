package com.walczak.itpoker.dto;

public record NewRoomResponseDTO(
        String roomId,
        String roomName,
        String playerId
) {
}
