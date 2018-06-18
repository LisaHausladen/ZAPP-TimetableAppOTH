package com.project.appproject.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TeacherDao {

    @Query("SELECT * FROM teachers")
    List<Teacher> getAll();

    @Insert
    void insert(Teacher teacher);

    @Delete
    void delete(Teacher teacher);
}
