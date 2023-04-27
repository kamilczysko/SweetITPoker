package com.walczak.itpoker.poker.room;

import com.walczak.itpoker.configuration.EvictionCache;
import com.walczak.itpoker.configuration.PokerLogger;
import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.SelectedCard;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RoomService {
    private final RoomRepository roomRepository;
    private final PokerLogger logger;
    private final EvictionCache evictionCache;

    public RoomService(RoomRepository roomRepository, PokerLogger logger) {
        this.roomRepository = roomRepository;
        this.logger = logger;
        long roomTTLInMinutes = 8 * 60;
        long refreshCacheTimeInMinutes = 2 * 60;
        this.evictionCache = EvictionCache.getInstance(roomTTLInMinutes, refreshCacheTimeInMinutes, this::expireRoom);
    }

    public Optional<Room> addPlayer(Player player, String roomId) {
        Optional<Room> foundRoom = roomRepository.findById(roomId);
        logger.info("Join room: " + roomId, player);
        evictionCache.refresh(roomId);
        return foundRoom
                .map(room -> prepareRoomUpdate(player, room))
                .map(roomRepository::save);
    }

    private static Room prepareRoomUpdate(Player player, Room room) {
        return new Room().builder(room)
                .addPlayer(player)
                .build();
    }

    public Room createNewRoom(Room room) {
        boolean scheduled = evictionCache.scheduleCleaningCache();
        if(scheduled) {
            logger.info("Eviction cache just scheduled");
        }
        logger.info("Create new room", room.toString());
        Room savedRoom = roomRepository.save(room);
        evictionCache.put(savedRoom.getId());
        return savedRoom;
    }

    public Room getExistingById(String roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new IllegalStateException("Room not found."));
    }

    public Optional<Room> getById(String roomId) {
        return roomRepository.findById(roomId);
    }

    public Iterable<Room> getAll() {
        return roomRepository.findAll();
    }

    public void deleteRoom(String id) {
        logger.info("Remove room", id);
        evictionCache.remove(id);
        roomRepository.deleteById(id);
    }

    public void expireRoom(String id) {
        logger.info("Room has expired", id);
        roomRepository.deleteById(id);
    }

    public Map<String, Double> calculateResult(String roomId) {
        List<Player> nonObservers = roomRepository.findById(roomId).map(Room::getPlayers).orElse(Collections.emptyList()).stream()
                .filter(player -> !player.isObserver()).collect(Collectors.toList());
        List<Player> players = nonObservers;
        if (nonObservers.stream().anyMatch(player -> player.getSelectedCard().value() != 0)) {
            players = nonObservers.stream()
                    .filter(player -> player.getSelectedCard().value() != 0)
                    .toList();
        }

        Map<String, List<Player>> roleToPlayers = players.stream()
                .collect(Collectors.groupingBy(Player::getRole));
        Map<String, Double> results = roleToPlayers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> calculateMeanTime(entry.getValue())));
        results.put("total", calculateMeanTime(players));
        return results;
    }

    private double calculateMeanTime(List<Player> players) {
        List<Float> times = players.stream().map(this::calculateTotalTimeByPlayer).toList();
        double sum = times.stream().mapToDouble(Float::floatValue).sum();
        return sum / times.size();
    }

    private float calculateTotalTimeByPlayer(Player player) {
        if (Objects.isNull(player.getSelectedCard())) {
            return 0f;
        }
        SelectedCard selectedCard = player.getSelectedCard();
        short value = selectedCard.value();
        String unit = selectedCard.unit();
        if (unit.equals("d")) {
            return value * 8.0f;
        }
        return value * 1.0f;
    }
}
