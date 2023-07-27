package com.walczak.itpoker.poker.player;

import com.walczak.itpoker.poker.room.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private short avatarIdx;
    private boolean isObserver;
    private boolean isAdmin;
    private String selectedCard;
    private String selectedUnit;
    private PlayerRole role;
    private boolean isObsoleted = false;
    public boolean hasSelectedCoffee() {
        return this.selectedCard.equals("c");
    }

    public boolean hasSelectedQuestion() {
        return this.selectedCard.equals("q");
    }

    public boolean hasSelectedNoValueCard() {
        return this.hasSelectedCoffee() || this.hasSelectedQuestion();
    }

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public Player cleanVote() {
        return this.toBuilder()
                .selectedCard(null)
                .selectedUnit(null).build();
    }
}
