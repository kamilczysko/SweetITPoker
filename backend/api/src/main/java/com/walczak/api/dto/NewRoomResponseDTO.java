package com.walczak.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewRoomResponseDTO {
    private String roomId;
    private String playerId;
}
