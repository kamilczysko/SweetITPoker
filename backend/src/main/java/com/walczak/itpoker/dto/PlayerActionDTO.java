package com.walczak.itpoker.dto;

public record PlayerActionDTO(
        String roomId,
        PlayerDTO modifiedPlayer,
        boolean resetAllVotes
) {

}
