package com.project.appproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class CustomiseTimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customise_timetable);
        Toolbar toolbar = findViewById(R.id.customise_timetable_toolbar);
        toolbar.setTitle(R.string.customise_timetable);
        setSupportActionBar(toolbar);
    }
}
