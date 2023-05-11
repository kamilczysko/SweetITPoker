package com.walczak.itpoker.poker.player;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
public final class SelectedCard {

    @Id
    private Long cardId;
    private String unit;
    private short timeValue;

    public SelectedCard(Long cardId, String unit, Short timeValue) {
        this.cardId = cardId;
        this.unit = unit;
        this.timeValue = timeValue;
    }

    public Long cardId() {
        return cardId;
    }

    public String unit() {
        return unit;
    }

    public short value() {
        return timeValue;
    }
}
