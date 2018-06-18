package com.project.appproject.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SubjectDao {

    @Query("SELECT * FROM subjects")
    List<Subject> getAll();

    @Insert
    void insert(Subject subject);

    @Insert
    void insertAll(Subject... subjects);

    @Delete
    void delete(Subject subject);
}

