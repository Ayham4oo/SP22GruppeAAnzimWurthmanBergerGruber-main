package com.lol.campusapp.data;

import com.lol.campusapp.SQLite.CalendarDataConnection;
import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.calendar.Event;
import com.lol.campusapp.utils.DataUtils;
import com.lol.campusapp.utils.DateTimeUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Calendar {
    private List<LectureInstance> pinnedLectureInstances = new LinkedList<>();

    private List<Event> eventsList = new ArrayList<>();

    /**
     * used to initialize the calendar (when first loading the user)
     * (necessary because other methods require an already set Current user, which isnt possible without loading the calendar)
     * @param e list of CurrentUsers Events
     * @param i list of CurrentUsers PinnedLectureInstances
     */
    public void setup(List<Event> e, List<LectureInstance> i){
        eventsList = e;
        pinnedLectureInstances = i;
    }

    /**
     * adds all Events of given Lecture Instance to the Calendar
     * also adds the instance to the PinnedLectures Table
     * @param instance the instance to be pinned
     */
    public void pinLectureInstance(LectureInstance instance){
        Lecture parent = new LectureDataConnection().getParent(instance);
        String title = instance.getForm() + " " + parent.getTitle();

        new CalendarDataConnection().addPinned(instance);
        LocalDate start = LocalDate.from(instance.getFirstDate());
        if(instance.getRhythm().equals(Rhythm.weekly)){
            while(start.isBefore(instance.getLastDate())){
                addEvent(new Event(title, start, instance.getStartTime()));
                start = start.plusWeeks(1);
            }
            addEvent(new Event(title, instance.getLastDate(), instance.getStartTime()));
        }else if(instance.getRhythm().equals(Rhythm.single) || instance.getRhythm().equals(Rhythm.block)){
            addEvent(new Event( title, instance.getFirstDate(),instance.getStartTime()));
        }else if(instance.getRhythm().equals(Rhythm.monthly)){
            while(start.isBefore(instance.getLastDate())){
                addEvent(new Event(title, start, instance.getStartTime()));
                start = start.plusMonths(1);
            }
        }else if(instance.getRhythm().equals(Rhythm.fortnightly)){
            while(start.isBefore(instance.getLastDate())){
                addEvent(new Event(title, start, instance.getStartTime()));
                start = start.plusWeeks(2);
            }
        }
        pinnedLectureInstances.add(instance);
    }

    /**
     * delets all Events of given Lecture instance from Calendar
     * also delets Instance from PinnedLectures in DB
     * @param instance
     */
    public void unPinLectureInstance(LectureInstance instance){
        Lecture parent = new LectureDataConnection().getParent(instance);
        String title = instance.getForm() + " "+ parent.getTitle();
        List<Event> remove = eventsList.stream().filter(event -> event.getName().equals(title)).collect(Collectors.toList());
        for(Event e : remove){
            removeEvent(e);
        }
        new CalendarDataConnection().removePinned(instance);
        pinnedLectureInstances.remove(instance);
    }

    public List<Event> getEvents(){
     return eventsList;
    }

    /**
     * adds an event to Users Calendar, also adds it into the DB
     * @param event to be added
     * @return success of operation
     */
    public boolean addEvent(Event event){
        new CalendarDataConnection().addEvent(event, DataUtils.instance.getCurrentUser().getApplogin().getUsername());
        return eventsList.add(event);
    }

    /**
     * removes an event to Users Calendar, also removes it from the DB
     * @param event to be removed
     * @return succes of operation
     */
    public boolean removeEvent(Event event){
        new CalendarDataConnection().removeEvent(event);
        return eventsList.remove(event);
    }

    public List<LectureInstance> getPinnedLectures() {
        return pinnedLectureInstances;
    }
}
