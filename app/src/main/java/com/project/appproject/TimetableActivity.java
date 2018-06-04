package com.project.appproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


public class TimetableActivity extends AppCompatActivity {
    String studyCourse;
    String[] studyCourses = new String[] {"IN1", "IN2", "IN3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = findViewById(R.id.timetable_toolbar);
        setSupportActionBar(toolbar);
        if(studyCourse == null) {

            onCreateDialog(savedInstanceState).show();
            TextView pickedStudyCourseText = findViewById(R.id.textViewStudyGroup);
            pickedStudyCourseText.setText(studyCourse);
            //pickedStudyCourseText.append(studyCourse);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timetable_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent1 = new Intent(this, TimetableSettingsActivity.class);
                startActivity(intent1);
                return true;

            case R.id.action_customise_timetable:
                // Show new activity "customise timetable"
                Intent intent2 = new Intent(this, CustomiseTimetableActivity.class);
                startActivity(intent2);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle(R.string.pick_studyCourse);
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
        builder.setSingleChoiceItems(studyCourses, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("TAG","ausgewählt");
                                studyCourse = studyCourses[which];
                                dialog.dismiss();
                                //TODO: geht noch nicht
                        }});

        return builder.create();
    }
}
