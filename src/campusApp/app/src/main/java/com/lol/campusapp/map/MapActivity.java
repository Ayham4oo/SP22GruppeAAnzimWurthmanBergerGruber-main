package com.lol.campusapp.map;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.map.lahnberge.LahnbergeActivity;
import com.lol.campusapp.map.lahntal.LahntalActivity;


public class MapActivity extends NavigableActivity {
    /**
     * administers two clickable images, switches activity to clicked part of map
     * all following part of the map consist of a image of a certain region of the university with
     * invisible buttons, leading to different parts of the displayed region
     */
    ImageView lahnberge;
    ImageView lahntal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        lahntal = findViewById(R.id.viewLahntal);
        lahnberge = findViewById(R.id.viewLahnberge);
        lahntal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(LahntalActivity.class);
            }
        });
        lahnberge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity(LahnbergeActivity.class);
            }
        });



    }
}