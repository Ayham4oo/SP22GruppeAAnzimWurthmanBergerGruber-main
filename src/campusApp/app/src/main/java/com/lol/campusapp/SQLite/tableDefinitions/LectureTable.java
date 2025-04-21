package com.lol.campusapp.SQLite.tableDefinitions;

public class LectureTable {
    public static String TABLE_NAME = "Lecture";
    public static final String COLUMN_VERSION_NR = "Version_Nr";
    public static final String COLUMN_TITLE = "COLUMN_NAME_TITLE";
    public static final String COLUMN_LECTURER = "Lecturer";
    public static final String COLUMN_SEMESTER = "Semester";
    public static final String COLUMN_MODULECODE = "ModulCode";
    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS "+  TABLE_NAME + " ( " +
                    COLUMN_VERSION_NR + " TEXT PRIMARY KEY, " +
                    COLUMN_TITLE +" TEXT, "+
                    COLUMN_LECTURER + " TEXT, " +
                    COLUMN_SEMESTER + " TEXT, " +
                    COLUMN_MODULECODE + " TEXT " +
                    ")";
}
