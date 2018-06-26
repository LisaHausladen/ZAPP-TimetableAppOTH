package com.project.appproject;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TimetableSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_settings);
        Toolbar toolbar = findViewById(R.id.timetable_settings_toolbar);
        toolbar.setTitle(R.string.settings);
        setSupportActionBar(toolbar);
    }

}
