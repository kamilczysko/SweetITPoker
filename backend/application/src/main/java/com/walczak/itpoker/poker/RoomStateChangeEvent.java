package com.walczak.itpoker.poker;


public class RoomStateChangeEvent {
    private final String roomId;
    private final boolean resetRoom;
    public RoomStateChangeEvent(String roomId, boolean resetRoom) {
        this.roomId = roomId;
        this.resetRoom = resetRoom;
    }

    public RoomStateChangeEvent(String roomId) {
        this.roomId = roomId;
        this.resetRoom = false;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean shouldResetRoom() {
        return this.resetRoom;
    }
}
