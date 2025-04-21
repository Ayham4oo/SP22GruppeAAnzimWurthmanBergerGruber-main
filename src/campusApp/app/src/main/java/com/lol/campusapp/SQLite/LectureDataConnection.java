package com.lol.campusapp.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lol.campusapp.SQLite.tableDefinitions.LectureInstanceTable;
import com.lol.campusapp.SQLite.tableDefinitions.LectureTable;
import com.lol.campusapp.data.Day;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.data.Rhythm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LectureDataConnection {

    /**
     * provides access ato all Tables regarding Lectures
     */
    private DataConnection conn;

    public LectureDataConnection(){
        conn = DataConnection.getConnection();
    }

    /** Will insert Lecture and cascade to inserting LectureInstances
     * (not yet insert reviews)
     */
    public boolean insertLecture(Lecture lecture) {
        return insertLectureWithOpenDB(conn.getWritableDatabase(), lecture);
    }

    /** Will insert Lecture and cascade to inserting LectureInstances into the given database
     * (not yet inset reviews)
     * @param db A Database that is already open (this is to prevent recursive opening of database
     *           in DataConnection.onCreate)
     */
    public boolean insertLectureWithOpenDB(SQLiteDatabase db, Lecture lecture) {
        boolean success = true;
        if (! insertLectureSurfaceWithOpenDB(db, lecture)) {success = false;}
        if (lecture.getLectureInstances() != null) {
            for (LectureInstance instance : lecture.getLectureInstances()) {
                if (!insertLectureInstanceWithOpenDB(db, lecture.getVersion_NR(), instance)) {
                    success = false;
                }
            }
        }
        return success;
    }

    /** Will insert surface level Lecture into the given database db
     * will not insert the lectureInstances and reviews
     * @param db A Database that is already open (this is to prevent recursive opening of database
     *            in DataConnection.onCreate)
     */
    public boolean insertLectureSurfaceWithOpenDB(SQLiteDatabase db, Lecture lecture) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LectureTable.COLUMN_VERSION_NR, lecture.getVersion_NR());
        contentValues.put(LectureTable.COLUMN_TITLE, lecture.getTitle());
        contentValues.put(LectureTable.COLUMN_LECTURER, lecture.getLecturer());
        contentValues.put(LectureTable.COLUMN_SEMESTER, lecture.getSemester());
        contentValues.put(LectureTable.COLUMN_MODULECODE, lecture.getModuleCode());

        return conn.insertWithOpenDB(db, LectureTable.TABLE_NAME, contentValues);
    }

    /** Will insert lectureInstance into the given Database db
     * @param db A Database that is already open (this is to prevent recursive opening of database
     *            in DataConnection.onCreate)
     */
    public boolean insertLectureInstanceWithOpenDB(SQLiteDatabase db,
                                                   String versionNR,
                                                   LectureInstance lectureInstance) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(LectureInstanceTable.COLUMN_LECTURE_VERNR, versionNR);
        contentValues.put(LectureInstanceTable.COLUMN_PARALELGROUP, lectureInstance.getParallelGroup());
        contentValues.put(LectureInstanceTable.COLUMN_ROOM, lectureInstance.getRoom());
        contentValues.put(LectureInstanceTable.COLUMN_DAY, lectureInstance.getDayString());
        contentValues.put(LectureInstanceTable.COLUMN_STARTTIME, lectureInstance.getStartTimeString());
        contentValues.put(LectureInstanceTable.COLUMN_ENDTIME, lectureInstance.getEndTimeString());
        contentValues.put(LectureInstanceTable.COLUMN_FIRSTDATE, lectureInstance.getFirstDateString());
        contentValues.put(LectureInstanceTable.COLUMN_LASTDATE, lectureInstance.getLastDateString());
        contentValues.put(LectureInstanceTable.COLUMN_FORM, lectureInstance.getForm());
        contentValues.put(LectureInstanceTable.COLUMN_RHYTHM, lectureInstance.getRhythm().toString());

        return conn.insertWithOpenDB(db, LectureInstanceTable.TABLE_NAME, contentValues);
    }

    @SuppressLint("Range")
    /**
     * Will return surface level Lecture from Database
     * reviews and lectureInstances will not automatically be loaded
     */
    public Lecture getLectureSurfaceByVersionNR(String verNr) {
        return getLectureSurfaceByVersionNRWithOpenDB(conn.getReadableDatabase(), verNr);
    }

    @SuppressLint("Range")
    /**
     * Will return surface level Lecture from Database
     * reviews and lectureInstances will not automatically be loaded
     */
    public Lecture getLectureSurfaceByVersionNRWithOpenDB(SQLiteDatabase db, String VerNr) {

        Lecture res = new Lecture();

        Cursor cursor = db.query(
                LectureTable.TABLE_NAME,   // The table to query
                null,           // The array of columns to return (pass null to get all)
                LectureTable.COLUMN_VERSION_NR + " = ?",              // The columns for the WHERE clause
                new String[] {VerNr},          // The values for the WHERE clause
                null,             // don't group the rows
                null,              // don't filter by row groups
                null               // The sort order
        );
        if (! cursor.moveToFirst()) {
            throw new RuntimeException("no Lecture with VerNR: " + VerNr);
        }

        res.setVersion_NR(VerNr);
        res.setTitle(cursor.getString(cursor.getColumnIndex(LectureTable.COLUMN_TITLE)));
        res.setLecturer(cursor.getString(cursor.getColumnIndex(LectureTable.COLUMN_LECTURER)));
        res.setSemester(cursor.getString(cursor.getColumnIndex(LectureTable.COLUMN_SEMESTER)));
        res.setModuleCode(cursor.getString(cursor.getColumnIndex(LectureTable.COLUMN_MODULECODE)));

        cursor.close();

        return res;
    }

    /** Returns all LectureInstances associated with a Lecture from the Database
     * @param versionNR of the Lecture to get LectureInstances from
     */
    @SuppressLint("Range")
    public Collection<LectureInstance> getLectureInstances(String versionNR) {
        SQLiteDatabase db = conn.getReadableDatabase();

        List<LectureInstance> res = new LinkedList<>();

        Cursor cursor = db.query(
                LectureInstanceTable.TABLE_NAME,
                null,
                LectureInstanceTable.COLUMN_LECTURE_VERNR + " = ?",
                new String[]{versionNR},
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
            LectureInstance instance = new LectureInstance();

            instance.setId(
                    cursor.getInt(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_ID)));

            instance.setParallelGroup(
                    cursor.getInt(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_PARALELGROUP)));

            instance.setRoom(
                    cursor.getString(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_ROOM)));

            String dayString = cursor.getString(cursor.getColumnIndex(LectureInstanceTable.COLUMN_DAY));
            instance.setDay(dayString.equals("null") ? null : Day.valueOf(dayString));

            instance.setStartTime(
                    cursor.getString(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_STARTTIME)));

            instance.setEndTime(
                    cursor.getString(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_ENDTIME)));

            instance.setFirstDate(
                    cursor.getString(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_FIRSTDATE)));

            instance.setLastDate(
                    cursor.getString(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_LASTDATE)));

            instance.setForm(
                    cursor.getString(cursor.getColumnIndex(
                            LectureInstanceTable.COLUMN_FORM)));

            instance.setRhythm(
                    Rhythm.valueOf(
                            cursor.getString(cursor.getColumnIndex(
                                    LectureInstanceTable.COLUMN_RHYTHM))));
            res.add(instance);
        }
        return res;
    }

    @SuppressLint("Range")
    /** returns all Lectures in the Database
     *  does not automatically load LectureInstances and Reviews
     */
    public Collection<Lecture> getAllLectureSurfaces() {
        return getAllLectureSurfacesWithOpenDB(conn.getReadableDatabase());
    }

    @SuppressLint("Range")
    /** returns all Lectures in the Database
     *  does not automatically load LectureInstances and Reviews
     */
    public Collection<Lecture> getAllLectureSurfacesWithOpenDB(SQLiteDatabase db) {
        List<Lecture> res = new LinkedList<>();
        Cursor cursor = db.query(
                LectureTable.TABLE_NAME,
                new String[] {LectureTable.COLUMN_VERSION_NR},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
            res.add(getLectureSurfaceByVersionNRWithOpenDB(
                    db,
                    cursor.getString(
                            cursor.getColumnIndex(
                                    LectureTable.COLUMN_VERSION_NR))));
        }
        return res;
    }

    /** returns all LectureInstances that are in the Database and are associated to a Lecture
     * (foreign Key Version_NR is not null)
     */
    public Collection<LectureInstance> getAllLectureInstances() {
        Collection<Lecture> allLectures = getAllLectureSurfaces();

        List<LectureInstance> result = new LinkedList<>();

        allLectures.forEach(lecture -> {
            result.addAll(getLectureInstances(lecture.getVersion_NR()));
        });

        return result;
    }

    /** return wheather the LectureInstance is Pinned to the Calendar
     * according to the Database
     * @param username identifies the User
     * @param lectureInstance the LectureInstance
     * @return true if pinned, false otherwise
     */
    public boolean isPinned(String username, LectureInstance lectureInstance) {
        throw new RuntimeException("not implemented");
    }

    /** For a given LectureInstance this returns the VersionNR of the Lecture containing the Instance
     * @param instance the given LectureInstance
     * @return the VersonNR of the corresponding Lecture
     */
    @SuppressLint("Range")
    public String getVersionNrToInstance(LectureInstance instance) {
        int id = instance.getId();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.query(
                LectureInstanceTable.TABLE_NAME,   // The table to query
                new String[] {LectureInstanceTable.COLUMN_LECTURE_VERNR},     // The array of columns to return (pass null to get all)
                LectureInstanceTable.COLUMN_ID + " = ?",              // The columns for the WHERE clause
                new String[] {Integer.toString(id)},          // The values for the WHERE clause
                null,             // don't group the rows
                null,              // don't filter by row groups
                null               // The sort order
        );
        if (! cursor.moveToFirst()) {
            throw new RuntimeException("no LectureInstance with ID: " + id);
        }
        String versionNR = cursor.getString(cursor.getColumnIndex(LectureInstanceTable.COLUMN_LECTURE_VERNR));
        cursor.close();
        return versionNR;
    }

    /** For a given LectureInstance this returns the ParentLecture that contains the instance
     * @param instance the given LectureInstance
     * @return the corresponding Lecture
     */
    public Lecture getParent(LectureInstance instance) {
        LectureDataConnection conn = new LectureDataConnection();
        String versionNR = conn.getVersionNrToInstance(instance);
        Lecture parent = conn.getLectureSurfaceByVersionNR(versionNR);
        parent.setLectureInstances(new ArrayList<>(conn.getLectureInstances(versionNR)));
        return parent;
    }
}
