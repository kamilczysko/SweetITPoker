package com.walczak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResultDTO {
    private String role;
    private double avg;
    private String unit;
}
