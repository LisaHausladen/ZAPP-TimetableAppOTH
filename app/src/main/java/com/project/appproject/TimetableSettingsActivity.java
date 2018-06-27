package com.project.appproject;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class TimetableSettingsActivity extends AppCompatActivity {
    SharedPreferences prefs;
    CheckBox hideRoomsBox;

    public static final String PREFS_KEY_SHORT_NAMES = "shortNames";
    public static final String PREFS_KEY_HIDE_ROOMS = "hideRooms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_settings);
        Toolbar toolbar = findViewById(R.id.timetable_settings_toolbar);
        toolbar.setTitle(R.string.settings);
        setSupportActionBar(toolbar);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useShortNames = prefs.getBoolean(PREFS_KEY_SHORT_NAMES, true);
        RadioButton radioButtonShortNames = findViewById(R.id.shortNames);
        RadioButton radioButtonLongNames = findViewById(R.id.longNames);
        if(useShortNames) {
            radioButtonShortNames.toggle();
        } else {
            radioButtonLongNames.toggle();
        }
        boolean hideRooms = prefs.getBoolean(PREFS_KEY_HIDE_ROOMS, false);
        hideRoomsBox = findViewById(R.id.hideRooms);
        if(hideRooms) {
            hideRoomsBox.toggle();
        }
    }

    public void hideRooms(View view) {
        SharedPreferences.Editor editor = prefs.edit();
        if(hideRoomsBox.isChecked()) {
            editor.putBoolean(PREFS_KEY_HIDE_ROOMS, true);
        } else {
            editor.putBoolean(PREFS_KEY_HIDE_ROOMS, false);
        }
        editor.commit();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        SharedPreferences.Editor editor = prefs.edit();
        switch(view.getId()) {
            case R.id.shortNames:
                if (checked)
                    editor.putBoolean(PREFS_KEY_SHORT_NAMES, true);
                    break;
            case R.id.longNames:
                if (checked)
                    editor.putBoolean(PREFS_KEY_SHORT_NAMES, false);
                    break;
        }
        editor.commit();
    }
}
