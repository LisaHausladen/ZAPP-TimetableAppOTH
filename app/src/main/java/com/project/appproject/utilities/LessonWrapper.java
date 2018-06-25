package com.project.appproject.utilities;

import com.project.appproject.database.Lesson;
import com.project.appproject.database.StudyGroup;

public class LessonWrapper {

    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private int[] su;
    private int[] ro;
    private int[] te;
    private int[] kl;

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

    public int[] getSu() {
        return su;
    }

    public void setSu(int[] su) {
        this.su = su;
    }

    public int[] getRo() {
        return ro;
    }

    public void setRo(int[] ro) {
        this.ro = ro;
    }

    public int[] getTe() {
        return te;
    }

    public void setTe(int[] te) {
        this.te = te;
    }

    public int[] getKl() {
        return kl;
    }

    public void setKl(int[] kl) {
        this.kl = kl;
    }

    public Lesson unwrap(StudyGroup studyGroup) {
        int subjectID = getSu()[0];
        int roomID = getRo()[0];
        int teacherID = getTe()[0];
        Lesson lesson = new Lesson(getId(),getDate(),getStartTime(), getEndTime(),
                subjectID, roomID, teacherID, studyGroup.getId());
        return lesson;
    }
}
