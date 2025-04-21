package com.lol.campusapp.data;

public class Review extends Lecture {
    private int stars; //0-5
    private String text;

    public int getStars() {
        return stars;
    }

    public void setStars(int start) {
        this.stars = start;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
