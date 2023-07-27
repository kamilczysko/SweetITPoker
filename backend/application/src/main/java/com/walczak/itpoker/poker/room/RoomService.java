package com.walczak.itpoker.poker.room;

import com.walczak.itpoker.configuration.PokerLogger;
import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.PlayerRole;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final PokerLogger pokerLogger;

    public RoomService(RoomRepository roomRepository, PokerLogger pokerLogger) {
        this.roomRepository = roomRepository;
        this.pokerLogger = pokerLogger;
    }

    public Room saveNewRoom(Room newRoom) {
        return roomRepository.save(newRoom);
    }

    public Room getRoom(String id) {
        return roomRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalStateException("Room does not exists"));
    }

    public void deleteRooms(List<Room> rooms) {
        roomRepository.deleteAll(rooms);
    }

    public Map<PlayerRole, Double> calculateRoomResult(String roomId) {
        Room room = getRoom(roomId);
        Map<PlayerRole, List<Player>> roleToPlayers = room.getPlayers().stream()
                .filter(player -> !player.isObserver())
                .filter(player -> Strings.isNotBlank(player.getSelectedCard()))
                .filter(player -> !player.hasSelectedNoValueCard())
                .collect(Collectors.groupingBy((Player::getRole)));
        return roleToPlayers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> ResultMapper.getAvg(entry.getValue())));
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void removeOldRooms() {
        Timestamp previous = Timestamp.from(Instant.now().minus(4, ChronoUnit.HOURS));
        List<Room> olderThan = roomRepository.findOlderThan(previous);
        pokerLogger.info("Sheduled remove old rooms: " + olderThan);
        deleteRooms(olderThan);
    }
}
