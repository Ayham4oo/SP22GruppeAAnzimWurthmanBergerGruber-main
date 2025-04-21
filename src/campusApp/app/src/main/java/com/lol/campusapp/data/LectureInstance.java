package com.lol.campusapp.data;

import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.utils.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

public class LectureInstance {
    public static final String NULL_STRING = "null";

    private  int id;
    private int parallelGroup;
    private String room;
    private Day day;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate firstDate;
    private LocalDate lastDate;
    private String form;
    private Rhythm rhythm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getParallelGroup() {
        return parallelGroup;
    }

    public void setParallelGroup(int parallelGroup) {
        this.parallelGroup = parallelGroup;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LectureInstance() {
    }

    public Day getDay() {
        return day;
    }

    public String getDayString() {
        return day != null ? day.toString() : NULL_STRING;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getStartTimeString() {
        return DateTimeUtils.printTime(startTime);
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getEndTimeString() {
        return DateTimeUtils.printTime(endTime);
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public String getFirstDateString() {
        return DateTimeUtils.printDate(firstDate);
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public String getLastDateString() {
        return DateTimeUtils.printDate(lastDate);
    }

    public Rhythm getRhythm() {
        return rhythm;
    }

    public String getRhythmString() {
        return rhythm != null ? rhythm.toString() : NULL_STRING;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = DateTimeUtils.parseTime(startTime);
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = DateTimeUtils.parseTime(endTime);
    }

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = DateTimeUtils.parseDate(firstDate);
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = DateTimeUtils.parseDate(lastDate);
    }

    public void setRhythm(Rhythm rhythm) {
        this.rhythm = rhythm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureInstance instance = (LectureInstance) o;
        return parallelGroup == instance.parallelGroup &&
                Objects.equals(room, instance.room) &&
                day == instance.day &&
                Objects.equals(startTime, instance.startTime) &&
                Objects.equals(endTime, instance.endTime) &&
                Objects.equals(firstDate, instance.firstDate) &&
                Objects.equals(lastDate, instance.lastDate) &&
                Objects.equals(form, instance.form) &&
                rhythm == instance.rhythm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parallelGroup, room, day, startTime, endTime, firstDate, lastDate, form, rhythm);
    }
}