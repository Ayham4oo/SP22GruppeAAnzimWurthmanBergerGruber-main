package com.lol.campusapp.lectureView;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.utils.ActivityUtils;
import com.lol.campusapp.utils.DataUtils;

import java.util.List;

public class MyLecturesActivity extends NavigableActivity {
    RecyclerView recyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        updateDisplay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.MERecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateDisplay() {
        List<Lecture> lectures = DataUtils.instance.getCurrentUser().getLectures();
        recyclerView.setAdapter(new LectureRecyclerViewAdapter(lectures));
    }
}