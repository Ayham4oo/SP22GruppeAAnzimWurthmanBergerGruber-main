package com.lol.campusapp.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lol.campusapp.SQLite.tableDefinitions.EventTable;
import com.lol.campusapp.SQLite.tableDefinitions.MyLecturesTable;
import com.lol.campusapp.SQLite.tableDefinitions.PinnedLecturesTable;
import com.lol.campusapp.SQLite.tableDefinitions.UserTable;
import com.lol.campusapp.calendar.Event;
import com.lol.campusapp.data.Applogin;
import com.lol.campusapp.data.Calendar;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.data.UniLogin;

import com.lol.campusapp.data.User;

import com.lol.campusapp.utils.DateTimeUtils;


import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * provides access ato all Tables regarding the Users
 *
 */
public class UserDataConnection {
    DataConnection conn;

    public UserDataConnection() {
        conn = DataConnection.getConnection();
    }

    /** will insert the username and the password into the database
     * @param userName identifies the user
     * @param password appPassword of the user
     * @return insertionSuccess
     */
    public Boolean insertAppLoginData(String userName, String password) {
        return insertAppLoginDataWithOpenDB(conn.getWritableDatabase(), userName, password);
    }

    /** will insert the username and the password into the database
     * @param db a database that is already open
     * @param userName identifies the user
     * @param password appPassword of the user
     * @return insertionSuccess
     */
    public Boolean insertAppLoginDataWithOpenDB(SQLiteDatabase db, String userName, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.COLUMN_APPUSERNAME, userName);
        contentValues.put(UserTable.COLUMN_APPPASSWORD, password);

        return conn.insertWithOpenDB(db, UserTable.TABLE_NAME, contentValues);
    }


    /** reads User from Database with AppLogin, UniLogin, MyLectures
     * @param username: identifies the User
     */
    @SuppressLint("Range")
    public User getUser(String username){
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.query(
                UserTable.TABLE_NAME,   // The table to query
                null,           // The array of columns to return (pass null to get all)
                UserTable.COLUMN_APPUSERNAME + " = ?",              // The columns for the WHERE clause
                new String[]{username},          // The values for the WHERE clause
                null,             // don't group the rows
                null,              // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToNext();
        Applogin applogin = new Applogin();
        applogin.setUsername(username);
        applogin.setPassword(cursor.getString(
                cursor.getColumnIndex(UserTable.COLUMN_APPPASSWORD)));

        UniLogin uniLogin = new UniLogin();
        uniLogin.setUsername(cursor.getString(
                cursor.getColumnIndex(UserTable.COLUMN_UNIUSERNAME)));
        uniLogin.setPassword(cursor.getString(
                cursor.getColumnIndex(UserTable.COLUMN_UNIPASSWORD)));

        List<Lecture> myLectures = getMyLecturesSurface(username);

        User user = new User();
        user.setApplogin(applogin);
        user.setUniLogin(uniLogin);
        user.setLectures(myLectures);
        Calendar calendar = new Calendar();
        calendar.setup(getMyEvents(username), getMyPinnedLectures(username));
        user.setCalendar(calendar);

        return user;
    }

    /**
     * @return saved login information for all accounts
     */
    public Map<String, String> getAllLogins() {
        Map<String, String> result = new HashMap<>();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.query(
                UserTable.TABLE_NAME,   // The table to query
                new String[]{UserTable.COLUMN_APPUSERNAME, UserTable.COLUMN_APPPASSWORD},           // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,             // don't group the rows
                null,              // don't filter by row groups
                null               // The sort order
        );
        while(cursor.moveToNext()){
            @SuppressLint("Range")
            String userName = cursor.getString(
                    cursor.getColumnIndex(UserTable.COLUMN_APPUSERNAME));
            @SuppressLint("Range")
            String password = cursor.getString(
                    cursor.getColumnIndex(UserTable.COLUMN_APPPASSWORD));
            result.put(userName, password);
        }
        return result;
    }

    /**
     * checks if login information matches with user in Database
     * @param userName to be checked
     * @param password to be checked
     * @return
     */
    public Boolean checkLogin(String userName, String password) {
        Map<String, String> userData = getAllLogins();
        return userData.containsKey(userName) && userData.get(userName).compareTo(password) == 0;
    }

    @SuppressLint("Range")
    /** Will return the lectures associated with the given user in the Database
     * Will not automatically load lectureInstances and Reviews to the lectures
     * @param username: username to identify the user for which to get the associated lectures
     */
    public List<Lecture> getMyLecturesSurface(String username){
        SQLiteDatabase db = conn.getReadableDatabase();
        List<Lecture> res = new LinkedList<>();
        Cursor cursor = db.query(
                MyLecturesTable.TABLE_NAME,
                null,
                MyLecturesTable.COLUMN_USERNAME + " = ?",
                new String[] {username},
                null,
                null,
                null
        );

        LectureDataConnection ldc = new LectureDataConnection();

        while (cursor.moveToNext()){
            res.add(ldc.getLectureSurfaceByVersionNR(
                    cursor.getString(
                            cursor.getColumnIndex(
                                    MyLecturesTable.COLUMN_VERSIONNR))));
        }
        return res;
    }
    /** In the Database remove reference to the Lecture in the myLectures-table
     * @param versionNR identifies the Lecture to be removed to myLectures.
     *                  The Lecture should already be in the lectures-table
     * @param userName identifies the User
     * @return the removalSuccess as boolean
     */
    public void removeFromMyLectures(String versionNR, String userName){
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DELETE FROM "+ MyLecturesTable.TABLE_NAME +
                    " WHERE "+ MyLecturesTable.COLUMN_USERNAME + " = '" + userName + "' AND " +
                    MyLecturesTable.COLUMN_VERSIONNR + " = '" + versionNR + "'"
        );
        db.close();
    }


    /** In the Database add reference to the Lecture in the myLectures-table
     * @param versionNR identifies the Lecture to be added to myLectures.
     *                  The Lecture should already be in the lectures-table
     * @param userName identifies the User
     * @return the insertionSuccess as boolean
     */
    public boolean addToMyLectures(String versionNR, String userName) {
        return addToMyLecturesWithOpenDB(conn.getWritableDatabase(), versionNR, userName);
    }

    /** In the Database add reference to the Lecture in the myLectures-table
     * @param db a database that is already open
     * @param versionNR identifies the Lecture to be added to myLectures.
     *                  The Lecture should already be in the lectures-table
     * @param userName identifies the User
     * @return the insertionSuccess as boolean
     */
    public boolean addToMyLecturesWithOpenDB(SQLiteDatabase db, String versionNR, String userName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyLecturesTable.COLUMN_USERNAME, userName);
        contentValues.put(MyLecturesTable.COLUMN_VERSIONNR, versionNR);
        return conn.insertWithOpenDB(db, MyLecturesTable.TABLE_NAME, contentValues);
    }


    @SuppressLint("Range")
    public List<Event> getMyEvents(String username){
        SQLiteDatabase db = conn.getReadableDatabase();
        LinkedList<Event> res = new LinkedList<>();
        Cursor cursor = db.query(
                EventTable.TABLE_NAME,
                null,
                EventTable.COLUMN_USER + " = ?",
                new String[] {username},
                null,
                null,
                null
        );
        CalendarDataConnection cdc = new CalendarDataConnection();
        while (cursor.moveToNext()){
            res.add(new Event(cursor.getString(cursor.getColumnIndex(EventTable.COLUMN_TITLE)),
                    DateTimeUtils.parseDate(cursor.getString(cursor.getColumnIndex(EventTable.COLUMN_DAY))),
                    DateTimeUtils.parseTime(cursor.getString(cursor.getColumnIndex(EventTable.COLUMN_TIME)))));
        }
        return res;
    }
    @SuppressLint("Range")
    public List<LectureInstance> getMyPinnedLectures(String username){
        SQLiteDatabase db = conn.getReadableDatabase();
        LinkedList<LectureInstance> res = new LinkedList<>();
        Cursor cursor = db.query(
                PinnedLecturesTable.TABLE_NAME,
                null,
                PinnedLecturesTable.COLUMN_USERNAME + " = ?",
                new String[] {username},
                null,
                null,
                null
        );

        Collection<LectureInstance> allInstances = new LectureDataConnection().getAllLectureInstances();
        while (cursor.moveToNext()){
            for(LectureInstance i : allInstances){
                if(i.getId() == cursor.getInt(cursor.getColumnIndex(PinnedLecturesTable.COLUMN_INSTANCE_ID))){
                    res.add(i);
                }
            }
        }
        return res;
    }

}