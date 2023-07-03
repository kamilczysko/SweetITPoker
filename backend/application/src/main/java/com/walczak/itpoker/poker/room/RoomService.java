package com.walczak.itpoker.poker.room;

import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.PlayerRole;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room saveNewRoom(Room newRoom) {
        return roomRepository.save(newRoom);
    }

    public Room getRoom(String id) {
        return roomRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalStateException("Room does not exists"));
    }

    public Map<PlayerRole, Double> calculateRoomResult(String roomId) {
        Room room = getRoom(roomId);
        Map<PlayerRole, List<Player>> roleToPlayers = room.getPlayers().stream()
                .filter(player -> !player.isObserver())
                .filter(player -> Strings.isNotBlank(player.getSelectedCard()))
                .collect(Collectors.groupingBy((Player::getRole)));
        return roleToPlayers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> ResultMapper.getAvg(entry.getValue())));
    }
}
