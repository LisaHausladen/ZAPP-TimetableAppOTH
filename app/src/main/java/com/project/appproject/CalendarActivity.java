package com.project.appproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = findViewById(R.id.calendar_toolbar);
        toolbar.setTitle(R.string.calendar);
        setSupportActionBar(toolbar);
        CalendarView calendar = findViewById(R.id.calendarView);
        //TODO Termine von Homepage einbinden
    }

}
