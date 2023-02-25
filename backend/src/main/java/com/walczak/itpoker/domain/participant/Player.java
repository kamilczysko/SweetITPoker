package com.walczak.itpoker.domain.participant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Optional;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    private String id;
    private String name;
    private short avatarIdx;
    private boolean isObserver;
    private boolean isAdmin;
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private SelectedCard selectedCard;
    private String role;
    @Column(columnDefinition = "boolean default false")
    private boolean obsoleted;

    private Player(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setAvatarIdx(builder.avatarIdx);
        setObserver(builder.isObserver);
        setAdmin(builder.isAdmin);
        setSelectedCard(builder.selectedCard);
        setRole(builder.role);
        setObsoleted(builder.obsoleted);
    }

    public static Builder builder(Player copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.avatarIdx = copy.getAvatarIdx();
        builder.isObserver = copy.isObserver();
        builder.isAdmin = copy.isAdmin();
        builder.selectedCard = copy.getSelectedCard();
        builder.role = copy.getRole();
        builder.obsoleted = copy.isObsoleted();
        return builder;
    }

    public boolean hasVoted() {
        return Optional.ofNullable(selectedCard).isPresent();
    }

    public static final class Builder {
        private String id;
        private String name;
        private short avatarIdx;
        private boolean isObserver;
        private boolean isAdmin;
        private SelectedCard selectedCard;
        private String role;
        private boolean obsoleted;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder avatarIdx(short avatarIdx) {
            this.avatarIdx = avatarIdx;
            return this;
        }

        public Builder isObserver(boolean isObserver) {
            this.isObserver = isObserver;
            return this;
        }

        public Builder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public Builder selectedCard(SelectedCard selectedCard) {
            this.selectedCard = selectedCard;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder obsoleted(boolean obsoleted) {
            this.obsoleted = obsoleted;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
