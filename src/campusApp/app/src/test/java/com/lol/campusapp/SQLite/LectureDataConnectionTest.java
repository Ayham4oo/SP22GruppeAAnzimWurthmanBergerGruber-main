package com.lol.campusapp.SQLite;

import static org.junit.Assert.*;

import android.app.Instrumentation;
import android.os.Bundle;

import androidx.test.platform.app.InstrumentationRegistry;

import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.utils.ContextHelper;

import org.junit.Before;
import org.junit.Test;



public class LectureDataConnectionTest {
    LectureDataConnection conn = new LectureDataConnection();

    @Before
    public void setUp(){

    }


    @Test
    public void getLectureSurfaceByVersionNR() {
        //Lecture lecture = conn.getLectureSurfaceByVersionNR("LV-12-105-012");
        //assertEquals(lecture.getTitle(), "Adaptive Numerische Verfahren für Operatorgleichungen");
    }

    @Test
    public void getLectureInstances() {
    }

    @Test
    public void getAllLectureSurfaces() {
    }
}