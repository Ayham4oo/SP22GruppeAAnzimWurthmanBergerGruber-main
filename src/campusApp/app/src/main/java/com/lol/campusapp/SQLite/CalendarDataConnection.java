package com.lol.campusapp.SQLite;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lol.campusapp.SQLite.tableDefinitions.EventTable;
import com.lol.campusapp.SQLite.tableDefinitions.LectureTable;
import com.lol.campusapp.SQLite.tableDefinitions.PinnedLecturesTable;
import com.lol.campusapp.calendar.Event;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.data.User;
import com.lol.campusapp.utils.ContextHelper;
import com.lol.campusapp.utils.DataUtils;
import com.lol.campusapp.utils.DateTimeUtils;

import java.util.List;

/**
 * provides access to all Tables regarding the calendar
 */

public class CalendarDataConnection {
    private DataConnection conn;

    public CalendarDataConnection(){
        conn = DataConnection.getConnection();
    }

    /**
     * adds a Lecture instance to PinnedLecturesTable
     * @param l instance to be added
     */
    public void addPinned(LectureInstance l){
        ContentValues cv = new ContentValues();
        cv.put(PinnedLecturesTable.COLUMN_USERNAME, DataUtils.instance.getCurrentUser().getApplogin().getUsername());
        cv.put(PinnedLecturesTable.COLUMN_INSTANCE_ID, l.getId());
        conn.insert(PinnedLecturesTable.TABLE_NAME, cv);
    }

    /**
     * removes A Lecture instance from PinnedLecturesTable
     * @param l to be removed
     */
    public void removePinned(LectureInstance l){
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DELETE FROM " + PinnedLecturesTable.TABLE_NAME + " WHERE " + PinnedLecturesTable.COLUMN_USERNAME + " = '" + DataUtils.instance.getCurrentUser().getApplogin().getUsername()
                + "' AND " + PinnedLecturesTable.COLUMN_INSTANCE_ID+ " = " + l.getId());
        db.close();
    }
    /**
     * adds an Event from the Event Table
     * @param e the Event to be added
     */
    public void addEvent(Event e, String username){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventTable.COLUMN_USER, username);
        contentValues.put(EventTable.COLUMN_TITLE, e.getName());
        contentValues.put(EventTable.COLUMN_DAY, DateTimeUtils.printDate(e.getDate()));
        contentValues.put(EventTable.COLUMN_TIME, DateTimeUtils.printTime(e.getTime()));
        conn.insert(EventTable.TABLE_NAME, contentValues);
    }

    /**
     * Removes an Event from the Event Table
     * @param e the Event to be removed
     */
    public void removeEvent(Event e){
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DELETE FROM " + EventTable.TABLE_NAME + " WHERE " + EventTable.COLUMN_USER + " = '" + DataUtils.instance.getCurrentUser().getApplogin().getUsername()
         + "' AND " + EventTable.COLUMN_DAY + " = '" + DateTimeUtils.printDate(e.getDate())
                + "' AND " + EventTable.COLUMN_TIME + " = '" + DateTimeUtils.printTime(e.getTime()) + "'");
        db.close();
    }
}
