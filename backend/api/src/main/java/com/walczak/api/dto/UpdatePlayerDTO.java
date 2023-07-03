package com.walczak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class UpdatePlayerDTO {
    private String id;
    private String name;
    private short avatarIdx;
    private boolean isObserver;
    private boolean isAdmin;
    private String selectedCard;
    private String selectedUnit;
    private String role;
}
