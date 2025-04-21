package com.lol.campusapp.search;

import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.search.LectureFilter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LectureInstanceFilterTest {
    LectureInstanceFilter filter = new LectureInstanceFilter();
    LectureInstance instance1;
    LectureInstance instance2;
    LectureInstance instance3;

    @Before
    public void setup() {
        List<LectureInstance> testInstances = new ArrayList<>();

        instance1 = new LectureInstance();
        instance1.setForm("Übung");
        instance1.setStartTime("14:00");
        instance1.setStartTime("18:00");
        instance1.setFirstDate("11.04.2017");
        instance1.setRoom("05C12");
        testInstances.add(instance1);

        instance2 = new LectureInstance();
        instance2.setForm("Vorlesung");
        instance2.setFirstDate("11.04.2000");
        instance2.setStartTime("14:00");
        instance2.setStartTime("16:00");
        instance2.setRoom("05A02");
        testInstances.add(instance2);

        instance3 = new LectureInstance();
        instance3.setForm("Seminar");
        instance3.setFirstDate("22.04.2022");
        instance3.setRoom("05C12");
        testInstances.add(instance3);

        filter.setAllObjects(testInstances);
    }

    @Test
    public void getSearchString() {
        String query1 = "seminar";
        List<LectureInstance> result1 = filter.getSearchResult(query1);
        assert result1.size() == 1;
        assert result1.get(0).equals(instance3);

        String query2 = "11.04";
        List<LectureInstance> result2 = filter.getSearchResult(query2);
        assert result2.size() == 2;
        LectureInstance firstOfResult2 = result2.get(0);
        LectureInstance secondOfResult2 = result2.get(1);
        if (firstOfResult2.equals(instance1)) {
            assert secondOfResult2.equals(instance2);
        } else if (firstOfResult2.equals(instance2)) {
            assert secondOfResult2.equals(instance1);
        } else {
            assert false;
        }

        String query3 = "05A02";
        List<LectureInstance> result3 = filter.getSearchResult(query3);
        assert result3.size() == 1;
        assert result3.get(0).equals(instance2);

        String query4 = "18:00";
        List<LectureInstance> result4 = filter.getSearchResult(query4);
        assert result4.size() == 1;
        assert result4.get(0).equals(instance1);
    }
}