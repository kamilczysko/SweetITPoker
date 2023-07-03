package com.walczak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPlayerDTO {
    private NewPlayerDTO player;
    private String roomId;
}
