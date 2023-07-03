package com.walczak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class NewRoomDTO {
    private String name;
    private List<String> cardsValues;
    private List<String> units;
    private NewPlayerDTO roomCreator;
}
