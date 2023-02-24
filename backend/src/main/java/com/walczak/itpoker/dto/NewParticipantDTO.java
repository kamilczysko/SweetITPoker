package com.walczak.itpoker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewParticipantDTO(
        String name,
        short avatarIdx,
        @JsonProperty("isAdmin")
        boolean isAdmin,
        @JsonProperty("isObserver")
        boolean isObserver,
        String role
) { }
