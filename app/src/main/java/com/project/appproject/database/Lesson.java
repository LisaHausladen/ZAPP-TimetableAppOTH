package com.project.appproject.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.project.appproject.database.Room;
import com.project.appproject.database.StudyGroup;
import com.project.appproject.database.Subject;
import com.project.appproject.database.Teacher;

import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "lessons")
public class Lesson {

    @PrimaryKey
    private int id;
    private String date;
    private String startTime;
    private String endTime;
    @ColumnInfo(name = "subjectID")
    private int subjectID;
    @ColumnInfo(name = "roomID")
    private int roomID;
    @ColumnInfo(name = "teacherID")
    private int teacherID;
    @ColumnInfo(name = "studyGroupID")
    private int studyGroupsID;

    public Lesson(int id, String date, String startTime, String endTime, int subjectID, int roomID, int teacherID, int studyGroupsID) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectID = subjectID;
        this.roomID = roomID;
        this.teacherID = teacherID;
        this.studyGroupsID = studyGroupsID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getStudyGroupsID() {
        return studyGroupsID;
    }

    public void setStudyGroupsID(int studyGroupsID) {
        this.studyGroupsID = studyGroupsID;
    }
}
