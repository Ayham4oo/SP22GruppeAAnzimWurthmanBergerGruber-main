package com.lol.campusapp.calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lol.campusapp.R;
import com.lol.campusapp.data.Calendar;
import com.lol.campusapp.utils.DataUtils;
import com.lol.campusapp.utils.DateTimeUtils;

import java.time.LocalTime;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;
    private int hour, minute = 100;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Datum: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Uhrzeit: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        if(hour != 100 && minute != 100) {
            String hourString = String.valueOf(hour);
            String minuteString = String.valueOf(minute);
            if(hourString.length() == 1) hourString = "0" + hourString;
            if(minuteString.length() == 1) minuteString = "0" + minuteString;
            time = DateTimeUtils.parseTime(hourString + ":" + minuteString);
        }
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        DataUtils.instance.getCurrentUser().getCalendar().addEvent(newEvent);
        finish();
    }

    int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

    public void timePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour , int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                eventTimeTV.setText("Time:" + String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}