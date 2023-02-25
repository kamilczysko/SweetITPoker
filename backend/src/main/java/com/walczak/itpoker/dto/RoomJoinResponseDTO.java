package com.walczak.itpoker.dto;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public record RoomJoinResponseDTO(
        String playerId,
        String roomId,
        String validation
) {
    public boolean isValid() {
        return Objects.isNull(validation) || Strings.isEmpty(validation);
    }
}
