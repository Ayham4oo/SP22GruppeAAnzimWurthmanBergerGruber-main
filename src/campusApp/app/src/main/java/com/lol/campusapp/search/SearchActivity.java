package com.lol.campusapp.search;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;

import com.lol.campusapp.SQLite.DataConnection;
import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.SQLite.UserDataConnection;
import com.lol.campusapp.calendar.EventAdapter;
import com.lol.campusapp.calendar.WeekViewActivity;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.lectureView.LectureActivity;
import com.lol.campusapp.lectureView.LectureInstanceRecyclerViewAdapter;
import com.lol.campusapp.lectureView.LectureInstanceModel;
import com.lol.campusapp.lectureView.LectureRecyclerViewAdapter;
import com.lol.campusapp.lectureView.LectureSurfaceModel;
import com.lol.campusapp.utils.ActivityUtils;
import com.lol.campusapp.utils.ContextHelper;
import com.lol.campusapp.utils.DataUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class SearchActivity extends NavigableActivity {
    EditText searchQueryEditText;
    RecyclerView recyclerView;
    Button startSearchButton;

    TextView lectureTextView;
    TextView lectureInstanceTextView;

    boolean lectureViewIsOpen = false;
    boolean lectureInstanceViewIsOpen = false;

    //these will contain the results of a search
    List<Lecture> lectureSearchResults;
    List<LectureInstance> lectureInstanceSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchQueryEditText = findViewById(R.id.Search_EditText);

        initLectureInstanceTextView();
        initLectureTextView();
        initStartSearchButton();
        initRecyclerView();
    }

    private void initLectureInstanceTextView() {
        lectureInstanceTextView = findViewById(R.id.Search_LectureInstanceTextView);
        lectureInstanceTextView.setOnClickListener(view -> showLectureInstanceResults());
        lectureInstanceTextView.setVisibility(View.GONE);
    }

    private void initLectureTextView() {
        lectureTextView = findViewById(R.id.Search_LectureTextView);
        lectureTextView.setOnClickListener(view -> showLectureResults());
        lectureTextView.setVisibility(View.GONE);
    }

    private void initStartSearchButton() {
        startSearchButton = findViewById(R.id.Search_StartButton);
        startSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.Search_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * is called when clicking LectureTextView
     */
    private void showLectureResults() {
        if (lectureSearchResults.size() == 0) {
            return;
        }

        if (!lectureViewIsOpen) {
            recyclerView.setVisibility(View.VISIBLE);
            lectureInstanceTextView.setVisibility(View.GONE);
            recyclerView.setAdapter(new LectureRecyclerViewAdapter(lectureSearchResults));
            lectureViewIsOpen = true;
        } else {
            setUpNeutralView();
            lectureViewIsOpen = false;
        }
    }

    /**
     * is called when clicking LectureInstanceTextView
     */
    private void showLectureInstanceResults() {
        if (lectureInstanceSearchResults.size() == 0) {
            return;
        }

        if (!lectureInstanceViewIsOpen) {
            recyclerView.setVisibility(View.VISIBLE);
            lectureTextView.setVisibility(View.GONE);
            LectureInstanceRecyclerViewAdapter adapter = new LectureInstanceRecyclerViewAdapter(
                    lectureInstanceSearchResults
            );

            //clicking on a lectureInstance will open the LectureActivity with that instance
            adapter.addItemOnClickListener( instance -> {
                String VersionNR = new LectureDataConnection().getVersionNrToInstance(instance);

                Intent intent = new Intent(ContextHelper.getContext(), LectureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("lecture_verNR", VersionNR);
                ContextHelper.getContext().startActivity(intent);
            });

            recyclerView.setAdapter(adapter);
            lectureInstanceViewIsOpen = true;
        } else {
            setUpNeutralView();
            lectureInstanceViewIsOpen = false;
        }
    }

    /**
     * is called in the OnClick of startSearchButton
     * will store results in {@code lectureSearchResults} and {@code lectureInstanceSearchResults}
     */
    private void startSearch() {
        String searchQuery = searchQueryEditText.getText().toString();
        lectureSearchResults = new LectureFilter().getSearchResult(searchQuery);
        lectureInstanceSearchResults = new LectureInstanceFilter().getSearchResult(searchQuery);

        setUpNeutralView();
    }

    @SuppressLint("SetTextI18n")
    private void setUpNeutralView() {
        //update the text and display the options
        lectureTextView.setText("Module "
                //R.string.Search_LectureTextViewStringBase
                + "(" + lectureSearchResults.size() + ")");
        lectureInstanceTextView.setText("Termine und Parallelgruppen "
                //R.string.Search_LectureInstanceTextViewStringBase
                + "(" + lectureInstanceSearchResults.size() + ")");

        lectureTextView.setVisibility(View.VISIBLE);
        lectureInstanceTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}