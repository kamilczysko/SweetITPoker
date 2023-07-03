package com.walczak.itpoker.poker.room;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {

    @Query("select r from Room r where r.createDate <= :createDate")
    List<Room> findOlderThan(@Param("createDate") Timestamp creationDateTime);
}
