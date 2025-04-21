package com.lol.campusapp.search;

import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.data.LectureInstance;

import java.util.ArrayList;
import java.util.List;

public class LectureInstanceFilter extends AbstractFilter<LectureInstance> {
    @Override
    List<LectureInstance> loadAllObjects() {
        LectureDataConnection conn = new LectureDataConnection();
        return new ArrayList<>(conn.getAllLectureInstances());
    }

    @Override
    String getSearchString(LectureInstance lectureInstance) {
        return lectureInstance.getParallelGroup() + "; " +
                lectureInstance.getRoom() + "; " +
                lectureInstance.getDayString() + "; " +
                lectureInstance.getStartTimeString() + "; " +
                lectureInstance.getEndTimeString() + "; " +
                lectureInstance.getFirstDateString() + "; " +
                lectureInstance.getLastDateString() + "; " +
                lectureInstance.getForm() + "; " +
                lectureInstance.getRhythmString() + ";";
    }
}
