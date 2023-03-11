package com.walczak.itpoker.domain.room;

import com.walczak.itpoker.domain.player.Player;
import com.walczak.itpoker.domain.player.SelectedCard;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> addPlayer(Player player, String roomId) {
        Optional<Room> foundRoom = roomRepository.findById(roomId);
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
        System.out.println(room);
        return roomRepository.save(room);
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
        roomRepository.deleteById(id);
    }

    public Map<String, Double> calculateResult(String roomId) {
        List<Player> nonObservers = roomRepository.findById(roomId).map(Room::getPlayers).orElse(Collections.emptyList()).stream()
                .filter(player -> !player.isObserver()).collect(Collectors.toList());
        List<Player> players = nonObservers;
        if(nonObservers.stream().anyMatch(player -> player.getSelectedCard().value() != 0)) {
            players = nonObservers.stream()
                    .filter(player -> player.getSelectedCard().value() != 0)
                    .toList();
        }

        Map<String, List<Player>> roleToPlayers = players.stream()
                .collect(Collectors.groupingBy(Player::getRole));
        return roleToPlayers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> calculateMeanTime(entry.getValue())));
    }

    private double calculateMeanTime (List<Player> players) {
        List<Float> times = players.stream().map(this::calculateTotalTimeByPlayer).toList();
        double sum = times.stream().mapToDouble(Float::floatValue).sum();
        return sum / times.size();
    }

    private float calculateTotalTimeByPlayer(Player player) {
        if (Objects.isNull(player.getSelectedCard())){
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
