package com.lol.campusapp;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lol.campusapp.calendar.CalendarActivity;
import com.lol.campusapp.lectureView.MyLecturesActivity;

import com.lol.campusapp.search.SearchActivity;

import com.lol.campusapp.map.MapActivity;

import com.lol.campusapp.mensa.MensaActivity;

/** This is an Activity that has a navitation menu to the other Activities in the upper right corner.
 * Activities that should have this menu aswell should just extend NavigableActivity instead of
 * AppCompatActivity
 */
public class NavigableActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dropdown_menu, menu);
        return true;
    }

    public void switchActivity(Class nextClass){
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.home:
                switchActivity(HomeActivity.class);
                return true;
            case R.id.myEvents:
                switchActivity(MyLecturesActivity.class);
                return true;
            case R.id.calendar:
                switchActivity(CalendarActivity.class);
                return true;
            case R.id.mensa:
                switchActivity(MensaActivity.class);
                return true;
            case R.id.mapCardView:
                switchActivity(MapActivity.class);
                return true;
            case R.id.search:
                switchActivity(SearchActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
