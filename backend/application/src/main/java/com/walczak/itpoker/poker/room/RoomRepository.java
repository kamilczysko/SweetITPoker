package com.walczak.itpoker.poker.room;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {
}
