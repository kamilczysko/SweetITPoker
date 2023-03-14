package com.walczak.itpoker.poker.player;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class SelectedCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
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
