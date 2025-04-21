package com.lol.campusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lol.campusapp.calendar.CalendarActivity;
import com.lol.campusapp.search.SearchActivity;
import com.lol.campusapp.loginAndRegister.LoginActivity;

import com.lol.campusapp.map.MapActivity;
import com.lol.campusapp.mensa.MensaActivity;
import com.lol.campusapp.lectureView.MyLecturesActivity;
import com.lol.campusapp.utils.DataUtils;


public class HomeActivity extends AppCompatActivity {
    private CardView myEvents;
    private CardView calendar;
    private CardView mensa;
    private CardView map;
    private CardView search;
    private CardView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myEvents = findViewById(R.id.mealCardView);
        calendar = findViewById(R.id.calendarCardView);
        mensa = findViewById(R.id.mensaCardView);
        map = findViewById(R.id.mapCardView);
        search = findViewById(R.id.searchCardView);
        logout = findViewById(R.id.logoutCardView);

        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchActivity(MyLecturesActivity.class);}
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchActivity(CalendarActivity.class);}
        });

        mensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchActivity(MensaActivity.class);}
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchActivity(MapActivity.class);}
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchActivity(SearchActivity.class);}
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtils.instance.setCurrentUser(null);
                switchActivity(LoginActivity.class);
            }
        });

    }

    public void switchActivity(Class nextClass){
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
    }
}