package com.walczak.itpoker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlayerDTO(
        String id,
        String name,
        short avatarIdx,
        @JsonProperty("isAdmin")
        boolean isAdmin,
        @JsonProperty("isObserver")
        boolean isObserver,
        @JsonProperty("selectedCard")
        SelectedCardDTO selectedCard,
        String role
) {
}
