package com.lol.campusapp.SQLite.tableDefinitions;

public class UserTable {
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_APPUSERNAME = "appUsername";
    public static final String COLUMN_APPPASSWORD = "appPassword";
    public static final String COLUMN_UNIUSERNAME = "uniUsername";;
    public static final String COLUMN_UNIPASSWORD = "uniPassword";
    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " +  TABLE_NAME +
                    " ( " + COLUMN_APPUSERNAME + " TEXT primary key, " +
                    COLUMN_APPPASSWORD + " TEXT," +
                    COLUMN_UNIUSERNAME + " TEXT," +
                    COLUMN_UNIPASSWORD + " TEXT" +
                    ")";
}
