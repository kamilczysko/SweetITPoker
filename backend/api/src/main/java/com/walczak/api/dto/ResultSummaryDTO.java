package com.walczak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResultSummaryDTO {
    private List<ResultDTO> results;
    @Builder.Default
    private boolean hasSelectedCoffee = false;
    @Builder.Default
    private boolean hasSelectedQuestion = false;
}
