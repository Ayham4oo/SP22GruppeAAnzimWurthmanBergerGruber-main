package com.lol.campusapp.lectureView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.SQLite.UserDataConnection;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.data.User;
import com.lol.campusapp.utils.ActivityUtils;
import com.lol.campusapp.utils.ContextHelper;
import com.lol.campusapp.utils.DataUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LectureActivity extends NavigableActivity {
    Lecture lecture;

    TextView title;
    TextView lecturer;
    TextView semester;
    Button toggle;
    RecyclerView recyclerView;

    List<LectureInstance> lectureInstances;
    List<LectureInstanceModel> lectureInstanceModels = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        getLectureAndInstances();

        title = findViewById(R.id.Lecture_TitleTextView);
        lecturer = findViewById(R.id.Lecture_LecturerTextView);
        semester = findViewById(R.id.Lecture_SemesterTextView);
        toggle = findViewById(R.id.Lecture_ToggleButton);
        setToggle();

        initRecyclerView();

        title.setText(lecture.getTitle());
        lecturer.setText("Verantw. Dozierende: " + lecture.getLecturer());
        semester.setText("Semester: " + lecture.getSemester());
    }

    /** will set the field lecture to the correct lectrue and will also load the LectureInstances into
     * the instances field of the lecture (lecture.getLectureInstances())
     */
    private void getLectureAndInstances() {
        Intent intent = getIntent();
        String lecture_verNR = intent.getStringExtra("lecture_verNR");
        LectureDataConnection conn = new LectureDataConnection();
        lecture = conn.getLectureSurfaceByVersionNR(lecture_verNR);
        lecture.setLectureInstances(new ArrayList<>(conn.getLectureInstances(lecture_verNR)));
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.lecture_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LectureInstanceRecyclerViewAdapter(lecture.getLectureInstances()));
    }

    /**
     * sets Status off Add/Remove to MyLectures button
     */
    private void setToggle(){
        Intent intent = getIntent();
        String lecture_verNR = intent.getStringExtra("lecture_verNR");
        User curr = DataUtils.instance.getCurrentUser();
        for(Lecture l : curr.getLectures()){
            if(l.getVersion_NR().equals(lecture_verNR)){
                toggle.setText(R.string.Lecture_removeFromMyLectures);
                toggle.setOnClickListener(view -> {
                    curr.getLectures().remove(l);
                    new UserDataConnection().removeFromMyLectures(lecture_verNR, curr.getApplogin().getUsername());
                    setToggle();
                });
                return;
            }
        }
        toggle.setText(R.string.Lecture_AddToMyLectures);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LectureDataConnection ldc = new LectureDataConnection();
                curr.getLectures().add(ldc.getLectureSurfaceByVersionNR(lecture_verNR));
                new UserDataConnection().addToMyLectures(lecture_verNR, curr.getApplogin().getUsername());
                setToggle();
            }
        });

    }
}
