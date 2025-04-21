package com.lol.campusapp.SQLite.tableDefinitions;

public class LectureInstanceTable {
    public static final String TABLE_NAME = "LectureInstanceTable";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PARALELGROUP = "ParalelGroup";
    public static final String COLUMN_ROOM = "Room";
    public static final String COLUMN_DAY = "Day";
    public static final String COLUMN_STARTTIME = "StartTime";
    public static final String COLUMN_ENDTIME = "EndTime";
    public static final String COLUMN_FIRSTDATE = "FirstDate";
    public static final String COLUMN_LASTDATE = "LastDate";
    public static final String COLUMN_FORM = "Form";
    public static final String COLUMN_RHYTHM = "Rhythm";
    public static final String COLUMN_LECTURE_VERNR = "LectureVerNR";

    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " +  TABLE_NAME + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_PARALELGROUP + " INTEGER," +
                    COLUMN_ROOM + " TEXT," +
                    COLUMN_DAY + " TEXT," +
                    COLUMN_STARTTIME + " TEXT," +
                    COLUMN_ENDTIME + " TEXT," +
                    COLUMN_FIRSTDATE + " TEXT," +
                    COLUMN_LASTDATE + " TEXT," +
                    COLUMN_FORM + " TEXT," +
                    COLUMN_RHYTHM + " TEXT," +
                    COLUMN_LECTURE_VERNR +" TEXT REFERENCES "+ LectureTable.TABLE_NAME +
                    "("+ LectureTable.COLUMN_VERSION_NR+")" +
                    ")";
}
