package com.lol.campusapp.SQLite.tableDefinitions;

public class MyLecturesTable {
    public static final String TABLE_NAME = "MyLectures";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_VERSIONNR = "VLNr";
    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS "+  TABLE_NAME + " ( " +
                    COLUMN_USERNAME + " TEXT REFERENCES "+ UserTable.TABLE_NAME + "("+ UserTable.COLUMN_APPUSERNAME +"), " +
                    COLUMN_VERSIONNR +" TEXT REFERENCES "+ LectureTable.TABLE_NAME + "("+ LectureTable.COLUMN_VERSION_NR+"), "+
                    " PRIMARY KEY ("+ COLUMN_USERNAME + ", " + COLUMN_VERSIONNR + "))";
}
