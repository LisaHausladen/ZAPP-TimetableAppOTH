package com.project.appproject.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LessonDao {
    @Query("SELECT * FROM lessons")
    List<Lesson> getAll();

    @Insert
    void insert(Lesson lesson);

    @Insert
    void insertAll(Lesson... lessons);

    @Update
    void updateAll(Lesson... lessons);

    @Delete
    void delete(Lesson lesson);
}
