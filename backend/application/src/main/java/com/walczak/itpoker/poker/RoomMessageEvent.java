package com.walczak.itpoker.poker;

public class RoomMessageEvent {
    private String roomId;
    private final String playerId;
    private final String message;

    private RoomMessageEvent(Builder builder) {
        roomId = builder.roomId;
        playerId = builder.playerId;
        message = builder.message;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMessage() {
        return message;
    }

    public static final class Builder {
        private String roomId;
        private String playerId;
        private String message;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder playerId(String playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public RoomMessageEvent build() {
            return new RoomMessageEvent(this);
        }
    }
}
