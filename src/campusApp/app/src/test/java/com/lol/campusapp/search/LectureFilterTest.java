package com.lol.campusapp.search;

import com.lol.campusapp.data.Lecture;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class LectureFilterTest {
    LectureFilter filter = new LectureFilter();
    Lecture lecture1;
    Lecture lecture2;
    Lecture lecture3;

    @Before
    public void setup() {
        List<Lecture> testLectures = new LinkedList<>();

        lecture1 = new Lecture();
        lecture1.setLecturer("Me and somebody else");
        lecture1.setSemester("SoSe 2020");
        lecture1.setModuleCode("(88-105-20162)");
        lecture1.setVersion_NR("LV-12-105-012");
        lecture1.setTitle("Adaptive Numerische Verfahren für Operatorgleichungen");
        testLectures.add(lecture1);

        lecture2 = new Lecture();
        lecture2.setLecturer("Nobody");
        lecture2.setSemester("SoSe 2012");
        lecture2.setModuleCode("(27-204-23412)");
        lecture2.setVersion_NR("LV-12-105-276");
        lecture2.setTitle("Elementare Topologie");
        testLectures.add(lecture2);

        lecture3 = new Lecture();
        lecture3.setLecturer("Nobody");
        lecture3.setSemester("SoSe 2012");
        lecture3.setModuleCode("(457-4554-45621)");
        lecture3.setVersion_NR("LV-15-200-276");
        lecture3.setTitle("Mathematische Datenanalyse");
        testLectures.add(lecture3);

        filter.setAllObjects(testLectures);
    }

    @Test
    public void getSearchResult() {

        String query1 = "daten";
        List<Lecture> result1 = filter.getSearchResult(query1);
        assert result1.size() == 1;
        assert result1.get(0).equals(lecture3);

        String query2 = "SoSe 2012";
        List<Lecture> result2 = filter.getSearchResult(query2);
        assert result2.size() == 2;
        Lecture resultLecture1 = result2.get(0);
        Lecture resultLecture2 = result2.get(1);
        if (resultLecture1.equals(lecture2)) {
            assert resultLecture2.equals(lecture3);
        } else if (resultLecture1.equals(lecture3)) {
            assert resultLecture2.equals(lecture2);
        } else {
            assert false;
        }

        String query3 = "SoMeBOdy";
        List<Lecture> result3 = filter.getSearchResult(query3);
        assert result3.size() == 1;
        assert result3.get(0).equals(lecture1);
    }
}