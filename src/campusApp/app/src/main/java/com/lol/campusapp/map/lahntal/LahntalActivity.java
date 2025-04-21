package com.lol.campusapp.map.lahntal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.map.lahntal.AussenanlagenActivity;
import com.lol.campusapp.map.lahntal.BiegenStrasseActivity;
import com.lol.campusapp.map.lahntal.FirmaneiActivity;
import com.lol.campusapp.map.lahntal.GutenbergstrasseActivity;
import com.lol.campusapp.map.lahntal.MarbacherWegActivity;
import com.lol.campusapp.map.lahntal.NordstadtActivity;
import com.lol.campusapp.map.lahntal.RenthofActivity;
import com.lol.campusapp.map.lahntal.SchlossbergActivity;
import com.lol.campusapp.map.lahntal.UniversitatsStr;
import com.lol.campusapp.map.lahntal.WilhelmRopkeStrActivity;

public class LahntalActivity extends NavigableActivity {

    private Button firmanei;
    private Button biegenStrasse;
    private Button aussenanlagen;
    private Button gutenbergstr;
    private Button marbacherweg;
    private Button nordstadt;
    private Button renthof;
    private Button schlossberg;
    private Button universitatsStr;
    private Button wilhelm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lahntal);

        firmanei = findViewById(R.id.firmanei);
        biegenStrasse = findViewById(R.id.biegenStrasse);
        aussenanlagen = findViewById(R.id.aussenanlagen);
        gutenbergstr = findViewById(R.id.gutenbergstr);
        marbacherweg = findViewById(R.id.marbacherWeg);
        nordstadt = findViewById(R.id.nordstadt);
        renthof = findViewById(R.id.renthof);
        schlossberg = findViewById(R.id.schlossberg);
        universitatsStr = findViewById((R.id.universitatsstr));
        wilhelm = findViewById(R.id.wilhelRopkeStr);

        wilhelm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(WilhelmRopkeStrActivity.class);
            }
        });

        universitatsStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(UniversitatsStr.class);
            }
        });

        schlossberg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(SchlossbergActivity.class);
            }
        });

        renthof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(RenthofActivity.class);
            }
        });

        nordstadt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(NordstadtActivity.class);
            }
        });

        marbacherweg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(MarbacherWegActivity.class);
            }
        });

        gutenbergstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(GutenbergstrasseActivity.class);
            }
        });

        aussenanlagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(AussenanlagenActivity.class);
            }
        });

        biegenStrasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(BiegenStrasseActivity.class);
            }
        });
        firmanei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity(FirmaneiActivity.class);
            }
        });

    }
}