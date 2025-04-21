package com.lol.campusapp.lectureView;

import com.lol.campusapp.data.Lecture;

public class LectureSurfaceModel {

    String titel;
    String semester;
    String versionNR;

    public LectureSurfaceModel(Lecture lecture){
        this.titel = lecture.getTitle();
        this.semester = lecture.getSemester();
        this.versionNR = lecture.getVersion_NR();
    }


    public String getTitel() {
        return titel;
    }

    public String getSemester() {
        return semester;
    }

    public String getVersionNR() {
        return versionNR;
    }
}

