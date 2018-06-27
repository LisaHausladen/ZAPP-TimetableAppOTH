package com.project.appproject.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT * FROM rooms")
    List<Room> getAll();

    @Insert
    void insert(Room room);

    @Insert
    void insertAll(Room... rooms);

    @Delete
    void delete(Room room);

    @Query("SELECT name FROM rooms WHERE id = :roomID")
    String getRoomById(int roomID);
}
