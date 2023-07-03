package com.walczak.itpoker.poker.room;

import com.walczak.itpoker.poker.player.PlayerRole;

import java.util.HashMap;
import java.util.Map;

public class RoomResultStage {
    private final Map<String, Map> roomToResult = new HashMap<>();

    public void setRoomInResultStage(String roomId, Map<PlayerRole, Double> result) {
        roomToResult.put(roomId, result);
    }

    public void removeRoomFromResultStage(String roomId) {
        roomToResult.remove(roomId);
    }

    public boolean hasResult(String roomId) {
        return roomToResult.containsKey(roomId);
    }

    public Map<PlayerRole, Double> getResult(String roomId) {
        return roomToResult.get(roomId);
    }
}
