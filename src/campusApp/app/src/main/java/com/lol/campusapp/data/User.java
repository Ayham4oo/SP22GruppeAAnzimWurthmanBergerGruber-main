package com.lol.campusapp.data;

import android.content.Context;

import com.lol.campusapp.SQLite.UserDataConnection;

import java.util.LinkedList;
import java.util.List;

public class User {

    private UniLogin uniLogin;
    private Applogin applogin;
    private List<Review> reviews  = new LinkedList<>();
    private List<Lecture> lectures = new LinkedList<>();
    private Calendar calendar = new Calendar();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public UniLogin getUniLogin() {
        return uniLogin;
    }

    public void setUniLogin(UniLogin uniLogin) {
        this.uniLogin = uniLogin;
    }

    public Applogin getApplogin() {
        return applogin;
    }

    public void setApplogin(Applogin applogin) {
        this.applogin = applogin;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
