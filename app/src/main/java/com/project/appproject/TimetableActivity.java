package com.project.appproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.project.appproject.database.StudyGroup;
import com.project.appproject.database.TimetableDatabase;
import com.project.appproject.utilities.NetworkUtils;

import java.util.ArrayList;


public class TimetableActivity extends AppCompatActivity {
    String studyGroup;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = findViewById(R.id.timetable_toolbar);
        setSupportActionBar(toolbar);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        studyGroup = prefs.getString("currentStudyGroup", null);
        if(studyGroup == null) {
            new SetupTimetableDataTask().execute(this);
            onCreateDialog(savedInstanceState).show();
        }
        updateStudyGroup();
    }

    private void updateStudyGroup() {
        TextView pickedStudyGroupText = findViewById(R.id.textViewStudyGroup);
        pickedStudyGroupText.setText(studyGroup);
        Toast toast = Toast.makeText(this, "you chose:" + studyGroup, Toast.LENGTH_LONG);
        toast.show();
        //ausgewählte Studiengruppe speichern:
    }

    private void saveStudyGroup() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("currentStudyGroup", studyGroup);
        editor.commit();
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
        //List<String> studyGroups = new ArrayList<>(TimetableDatabase.getInstance(this).studyGroupDao().getStudyGroupNames());
        //final String[] groups = studyGroups.toArray(new String[0]);
        final String[] groups = new String[] {"IN1", "IN2", "IN3", "IN4", "IN5", "IN6", "IN7"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle(R.string.pick_studyCourse);
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
        builder.setSingleChoiceItems(groups, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("TAG","ausgewählt");
                                if(groups.length > 0) {studyGroup = groups[which];}
                                dialog.dismiss();
                                saveStudyGroup();
                                updateStudyGroup();
                                //TODO: createLessons();
                        }});

        return builder.create();
    }

    public void createLessons() {
        ArrayList<StudyGroup> studyGroups =
                new ArrayList<StudyGroup>(TimetableDatabase.getInstance(this).studyGroupDao().getINStudyGroups());
        for (StudyGroup group : studyGroups) {
            if(group.getName().equals(studyGroup)) {
                new NetworkUtils().getLessons(group);
            } else {
                System.out.println("no match found!");;
            }
        }
    }

}
