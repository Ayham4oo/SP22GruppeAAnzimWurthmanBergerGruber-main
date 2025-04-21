package com.lol.campusapp.lectureView;

import com.lol.campusapp.data.LectureInstance;

public class LectureInstanceModel {
    LectureInstance instance;

    public LectureInstanceModel(LectureInstance instance){
        this.instance = instance;
    }

    public String getParallelGroupAndForm() {
        return String.format("%s: Parallelgruppe %d",
                instance.getForm(),
                instance.getParallelGroup()
        );
    }
    public LectureInstance getInstance(){
        return instance;
    }

    public String getRoom() {
        return "Raum: " + instance.getRoom();
    }

    public String getTime() {
        return String.format("%s, %s - %sUhr",
                instance.getDayString(),
                instance.getStartTimeString(),
                instance.getEndTimeString()
        );
    }

    public String getDateAndRhythem() {
        String rythmString = "";
        switch (instance.getRhythm()) {
            case single:
                return "Einzeltermin am: "+ instance.getFirstDateString();
            case block:
                rythmString = "Blockveranstaltung";
                break;
            case weekly:
                rythmString = "Wöchentlich";
                break;
            case monthly:
                rythmString = "Monatlich";
                break;
            case fortnightly:
                rythmString = "Alle zwei Wochen";
                break;
        }
        return rythmString + "\n" +
                String.format("Von: %s\nBis: %s",
                instance.getFirstDateString(),
                instance.getLastDateString()
        );
    }
}
