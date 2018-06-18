package com.project.appproject.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StudyGroupDao {

    @Query("SELECT * FROM studyGroups")
    List<StudyGroup> getAll();

    @Insert
    void insert(StudyGroup studyGroup);

    @Delete
    void delete(StudyGroup studyGroup);

    @Query("SELECT * FROM studyGroups WHERE name LIKE 'IN_'")
    List<StudyGroup> getINStudyGroups();

}
