package com.lol.campusapp.data;

import java.util.LinkedList;
import java.util.List;

public class Lecture {
    public static final String NULL_STRING = "null";

    private List<LectureInstance> lectureInstances = new LinkedList<>();
    private String Version_NR;
    private String title;
    private String moduleCode;
    private String lecturer;
    private String Semester;
    private List<Review> reviews = new LinkedList<>();

    public List<Review> getReviews() {
        return reviews;
    }

    public String getVersion_NR() {
        return printString(Version_NR);
    }

    public String getSemester() {
        return printString(Semester);
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public void setLectureInstances(List<LectureInstance> lectureInstances) {
        this.lectureInstances = lectureInstances;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getModuleCode() {
        return printString(moduleCode);
    }

    public List<LectureInstance> getLectureInstances() {
        return lectureInstances;
    }

    public String getLecturer() {
        return printString(lecturer);
    }

    public String getTitle() {
        return title;
    }

    public void setVersion_NR(String version_NR) {
        this.Version_NR = version_NR;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    private String printString(String str) {
        return str != null ? str : NULL_STRING;
    }
}

