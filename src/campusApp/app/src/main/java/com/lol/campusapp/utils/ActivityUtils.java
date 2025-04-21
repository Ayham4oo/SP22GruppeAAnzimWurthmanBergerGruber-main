package com.lol.campusapp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lol.campusapp.HomeActivity;
import com.lol.campusapp.SQLite.UserDataConnection;
import com.lol.campusapp.data.Applogin;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.data.User;
import com.lol.campusapp.lectureView.LectureActivity;
import com.lol.campusapp.lectureView.LectureInstanceModel;
import com.lol.campusapp.lectureView.LectureInstanceRecyclerViewAdapter;
import com.lol.campusapp.lectureView.LectureRecyclerViewAdapter;
import com.lol.campusapp.lectureView.LectureSurfaceModel;
import com.lol.campusapp.lectureView.MyLecturesActivity;
import com.lol.campusapp.loginAndRegister.LoginActivity;
import com.lol.campusapp.loginAndRegister.RegisterActivity;
import com.lol.campusapp.mensa.MensaDataConnection;
import com.lol.campusapp.mockData.MockDataReader;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides functions often used by different activities
 * is implemented following the Singleton Pattern
 */
public class ActivityUtils {
    public static ActivityUtils instance = new ActivityUtils();

    private ActivityUtils(){}

    /**
     * creates a Text view with given outPut text (unifies layout for Textviews)
     * @param outputText text to be displayed
     * @param textView view to be displayed
     */ // used by Register and Login Activity
    public void wrongInput(String outputText, TextView textView){
        textView.setTextSize(15);
        textView.setTextColor(Color.parseColor("#B51010"));
        textView.setText(outputText);
    }

    /**
     * switches to a different activity
     * @param activity the activity that initializes the switch
     * @param nextClass the class to be switched to
     */
    public void switchActivity(AppCompatActivity activity, Class nextClass){
        if (nextClass == HomeActivity.class &&
            (activity instanceof RegisterActivity || activity instanceof LoginActivity)) {
            //switching from register or login to home
            MensaDataConnection.preloadDoc();
        }
        Intent intent = new Intent(activity, nextClass);
        activity.startActivity(intent);
    }
}
