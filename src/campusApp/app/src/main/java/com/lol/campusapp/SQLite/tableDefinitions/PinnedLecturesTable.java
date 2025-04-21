package com.lol.campusapp.SQLite.tableDefinitions;

public class PinnedLecturesTable {
    public static final String TABLE_NAME = "PinnedLectures";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_INSTANCE_ID = "VLNr";
    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS "+  TABLE_NAME + " ( " +
                    COLUMN_USERNAME + " TEXT REFERENCES "+ UserTable.TABLE_NAME + "("+ UserTable.COLUMN_APPUSERNAME +"), " +
                    COLUMN_INSTANCE_ID +" INTEGER REFERENCES "+ LectureInstanceTable.TABLE_NAME + "("+ LectureInstanceTable.COLUMN_ID+"), "+
                    " PRIMARY KEY ("+ COLUMN_USERNAME + ", " + COLUMN_INSTANCE_ID + "))";
}

