package com.walczak.itpoker.domain.room;

import com.walczak.itpoker.domain.participant.Player;
import org.springframework.stereotype.Controller;

import java.util.Optional;

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
                .addParticipant(player)
                .build();
    }

    public Room createNewRoom(Room room) {
        System.out.println(room);
        return roomRepository.save(room);
    }
    public Room updateRoom(Room room) {
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
}
