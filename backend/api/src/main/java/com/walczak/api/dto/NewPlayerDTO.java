package com.walczak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class NewPlayerDTO {
    private String name;
    private short avatarIdx;
    private String role;
    private String roomId;
}
