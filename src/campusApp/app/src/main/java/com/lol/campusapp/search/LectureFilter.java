package com.lol.campusapp.search;

import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.data.Lecture;

import java.util.ArrayList;
import java.util.List;

public class LectureFilter extends AbstractFilter<Lecture> {
    @Override
    List<Lecture> loadAllObjects() {
        LectureDataConnection conn = new LectureDataConnection();
        return new ArrayList<>(conn.getAllLectureSurfaces());
    }

    @Override
    String getSearchString(Lecture lecture) {
        return lecture.getTitle() + "; " +
                lecture.getLecturer() + "; " +
                lecture.getModuleCode() + "; " +
                lecture.getSemester() + "; " +
                lecture.getVersion_NR() + ";";
    }
}
