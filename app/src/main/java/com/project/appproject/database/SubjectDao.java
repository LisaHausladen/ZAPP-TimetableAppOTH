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

    @Query("SELECT name FROM subjects WHERE id = :subjectID")
    String getSubjectNameById(int subjectID);

    @Query("SELECT longName FROM subjects WHERE id = :subjectID")
    String getSubjectLongNameById(int subjectID);
}

