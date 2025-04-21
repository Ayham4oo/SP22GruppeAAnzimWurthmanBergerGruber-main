package com.lol.campusapp.mensa;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Meal {
    private LocalDate date;
    private String title;   //eg "Nudeln mit ... und ..."
    private String type;    //eg "Tagesgericht"
    private float price;    //

    public Meal(){}

    public String printPrice() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(price) + " €";
    }

    public Meal(LocalDate date, String title, String type, float price) {
        this.date = date;
        this.title = title;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price + "€" +
                '}';
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
