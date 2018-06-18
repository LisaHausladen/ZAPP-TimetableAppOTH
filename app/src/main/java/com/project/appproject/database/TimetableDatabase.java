package com.project.appproject.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StudyGroup.class, com.project.appproject.database.Room.class, Subject.class, Teacher.class}, version = 1)
public abstract class TimetableDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "timetable";

    private static final Object LOCK = new Object();
    private static volatile TimetableDatabase instance;

    public static TimetableDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        TimetableDatabase.class,
                        TimetableDatabase.DATABASE_NAME).allowMainThreadQueries().build();
            }
        }
        return instance;
    }

    public abstract StudyGroupDao studyGroupDao();

    public abstract SubjectDao subjectDao();

    public abstract RoomDao roomDao();

    public abstract TeacherDao teacherDao();

}
