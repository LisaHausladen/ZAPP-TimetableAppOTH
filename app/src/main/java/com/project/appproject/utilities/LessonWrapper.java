package com.project.appproject.utilities;

import com.project.appproject.database.Lesson;
import com.project.appproject.database.Room;
import com.project.appproject.database.StudyGroup;
import com.project.appproject.database.Subject;
import com.project.appproject.database.Teacher;

public class LessonWrapper {

    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private Subject[] su;
    private Room[] ro;
    private Teacher[] te;
    private StudyGroup[] kl;

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

    public Subject[] getSu() {
        return su;
    }

    public void setSu(Subject[] su) {
        this.su = su;
    }

    public Room[] getRo() {
        return ro;
    }

    public void setRo(Room[] ro) {
        this.ro = ro;
    }

    public Teacher[] getTe() {
        return te;
    }

    public void setTe(Teacher[] te) {
        this.te = te;
    }

    public StudyGroup[] getKl() {
        return kl;
    }

    public void setKl(StudyGroup[] kl) {
        this.kl = kl;
    }

    public Lesson unwrap(StudyGroup studyGroup) {
        Subject subject = getSu()[0];
        Room room = getRo()[0];
        Teacher teacherID = getTe()[0];
        Lesson lesson = new Lesson(getId(),getDate(),getStartTime(), getEndTime(),
                subject.getId(), room.getId(), teacherID.getId(), studyGroup.getId());
        return lesson;
    }
}
