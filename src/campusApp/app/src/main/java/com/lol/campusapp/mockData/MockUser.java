package com.lol.campusapp.mockData;

import android.database.sqlite.SQLiteDatabase;

import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.SQLite.UserDataConnection;

public class MockUser {
    public static MockUser instance = new MockUser();
    public static final String USERNAME = "abc";
    public static final String PASSWORD = "12345678";

    private MockUser() {

    }

    /** will insert a MockUser into the given db
     * will only add lectures to mylectures if there are already som in the database
     * @param db: the database
     */
    public void loadMockUserToDB(SQLiteDatabase db) {
        UserDataConnection userDataConnection = new UserDataConnection();
        userDataConnection.insertAppLoginDataWithOpenDB(db, USERNAME, PASSWORD);

        LectureDataConnection lectureDataConnection = new LectureDataConnection();
        lectureDataConnection.getAllLectureSurfacesWithOpenDB(db).stream().limit(20).forEach(lecture -> {
            userDataConnection.addToMyLecturesWithOpenDB(db, lecture.getVersion_NR(), USERNAME);
        });
    }
}
