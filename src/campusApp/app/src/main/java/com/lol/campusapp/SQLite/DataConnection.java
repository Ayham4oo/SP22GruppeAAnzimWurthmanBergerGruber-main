package com.lol.campusapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.lol.campusapp.SQLite.tableDefinitions.EventTable;
import com.lol.campusapp.SQLite.tableDefinitions.LectureInstanceTable;
import com.lol.campusapp.SQLite.tableDefinitions.LectureTable;
import com.lol.campusapp.SQLite.tableDefinitions.LectureTable;
import com.lol.campusapp.SQLite.tableDefinitions.MyLecturesTable;
import com.lol.campusapp.SQLite.tableDefinitions.PinnedLecturesTable;
import com.lol.campusapp.SQLite.tableDefinitions.UserTable;
import com.lol.campusapp.mockData.MockDataReader;
import com.lol.campusapp.mockData.MockUser;
import com.lol.campusapp.utils.ContextHelper;

import java.nio.channels.GatheringByteChannel;

/**
 * provides access to Database
 * used by all other _DataConnection classes
 */

public class DataConnection extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SQLite_DB";

    //muss inkrementiert werden, wenn schema der DB verändert wird
    //Gespeicherte Daten werden (nach aktueller Def von onUpgrade) dadurch gelöscht
    public static final int DATABASE_VERSION = 13;


    private static DataConnection instance;


    public static synchronized DataConnection getConnection() {
        if (instance == null)
            instance = new DataConnection();
        return instance;
    }

    private DataConnection(){
        super(ContextHelper.getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.CREATE_SQL);
        db.execSQL(LectureTable.CREATE_SQL);
        db.execSQL(MyLecturesTable.CREATE_SQL);
        db.execSQL(LectureInstanceTable.CREATE_SQL);
        db.execSQL(PinnedLecturesTable.CREATE_SQL);
        db.execSQL(EventTable.CREATE_SQL);

        MockDataReader.instance.loadSampleDataToDB(db);
        //MockUser.instance.loadMockUserToDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LectureTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MyLecturesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LectureInstanceTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EventTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PinnedLecturesTable.TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String tableName, ContentValues contentValues) {
        return insertWithOpenDB(getWritableDatabase(), tableName, contentValues);
    }

    public boolean insertWithOpenDB(SQLiteDatabase db, String tableName, ContentValues contentValues) {
        boolean insertionSuccess = -1 != db.insert(
                tableName,
                null,
                contentValues);
        return insertionSuccess;
    }

}
