package com.lol.campusapp.calendar;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.lol.campusapp.data.Calendar;
import com.lol.campusapp.utils.DataUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : DataUtils.instance.getCurrentUser().getCalendar().getEvents()) {
            if(event.getDate().equals(date))
                events.add(event);
        }
        return events;
    }

    private String name;
    private LocalDate date;
    private LocalTime time;

    public Event(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
