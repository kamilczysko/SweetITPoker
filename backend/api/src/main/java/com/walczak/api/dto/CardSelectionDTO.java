package com.walczak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CardSelectionDTO {
    private String playerId;
    private String cardValue;
    private String unit;
}
