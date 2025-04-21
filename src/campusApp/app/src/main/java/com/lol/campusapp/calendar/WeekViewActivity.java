package com.lol.campusapp.calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.lol.campusapp.calendar.CalendarUtils.daysInMonthArray;
import static com.lol.campusapp.calendar.CalendarUtils.daysInWeekArray;
import static com.lol.campusapp.calendar.CalendarUtils.monthYearFromDate;

import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.utils.DataUtils;

public class WeekViewActivity extends NavigableActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private ArrayList<Event> dailyEvents = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
        dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                new AlertDialog.Builder(WeekViewActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Bist du sicher?")
                        .setMessage("Willst du dieses Ereignis löschen?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataUtils.instance.getCurrentUser().getCalendar().removeEvent(dailyEvents.get(position));
                                dailyEvents.remove(position);
                                EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
                                eventListView.setAdapter(eventAdapter);
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return true;
            }
        });
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this, CalendarActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater() {
        dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}