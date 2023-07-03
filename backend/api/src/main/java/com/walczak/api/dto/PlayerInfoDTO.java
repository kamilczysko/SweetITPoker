package com.walczak.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class PlayerInfoDTO {
    private String id;
    private String name;
    private short avatarIdx;
    @JsonProperty("isObserver")
    private boolean isObserver;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    private String selectedCard;
    private String selectedUnit;
    private String role;
    @JsonProperty("isObsoleted")
    private boolean isObsoleted = false;
    private String roomId;
}
