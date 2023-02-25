package com.walczak.itpoker.dto;

import java.util.List;

public record RoomDTO(
        String roomId,
        String roomName,
        List<PlayerDTO> players
) {
}
