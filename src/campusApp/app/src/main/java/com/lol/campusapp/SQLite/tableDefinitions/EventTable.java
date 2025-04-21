package com.lol.campusapp.SQLite.tableDefinitions;

public class EventTable {
    public static final String TABLE_NAME = "Event";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_DAY = "Day";
    public static final String COLUMN_TIME = "Time";
    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " +  TABLE_NAME + " ( " +
                    COLUMN_USER + " TEXT REFERENCES " + UserTable.TABLE_NAME +
                    "(" + UserTable.COLUMN_APPUSERNAME + ")" + "," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_DAY + " TEXT," +
                    COLUMN_TIME + " TEXT," +
                    " PRIMARY KEY ("+ COLUMN_USER + ", " + COLUMN_TITLE + "," + COLUMN_DAY +  "," + COLUMN_TIME + "))"
                    ;
}
