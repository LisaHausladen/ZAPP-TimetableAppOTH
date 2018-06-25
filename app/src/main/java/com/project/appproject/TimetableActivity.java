package com.project.appproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.appproject.database.StudyGroup;
import com.project.appproject.database.TimetableDatabase;
import com.project.appproject.utilities.NetworkUtils;

import java.util.ArrayList;


public class TimetableActivity extends AppCompatActivity {
    String studyGroup;
    SharedPreferences prefs;
    private Context context;
    private ProgressBar taskProgressBar;
    private TextView taskTextView;

    private static final String PREFS_KEY_STUDYGROUP = "currentStudyGroup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = findViewById(R.id.timetable_toolbar);
        toolbar.setTitle(R.string.timetable);
        setSupportActionBar(toolbar);
        taskProgressBar = findViewById(R.id.taskProgressBar);
        taskTextView = findViewById(R.id.taskTextView);
        context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        studyGroup = prefs.getString(PREFS_KEY_STUDYGROUP, null);
        if(studyGroup == null) {
            new SetupTimetableDataTask().execute();
            onCreateDialog(savedInstanceState).show();
        }
        updateStudyGroup();
    }

    private void updateStudyGroup() {
        TextView pickedStudyGroupText = findViewById(R.id.textViewStudyGroup);
        if(studyGroup != null && pickedStudyGroupText != null) {
            pickedStudyGroupText.setText(studyGroup);
        }
        Toast toast = Toast.makeText(this, "you chose:" + studyGroup, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void saveStudyGroup() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_KEY_STUDYGROUP, studyGroup);
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
        final String[] groups = new String[] {"I1", "I2", "I3", "I4", "I5", "I6", "I7", "IM1", "IM2"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pick_studyCourse);
        builder.setSingleChoiceItems(groups, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(groups.length > 0) {studyGroup = groups[which];}
                                dialog.dismiss();
                                saveStudyGroup();
                                updateStudyGroup();
                        }});

        return builder.create();
    }

    public void createLessonList() {
        ArrayList<StudyGroup> studyGroups =
                new ArrayList<>(TimetableDatabase.getInstance(this).studyGroupDao().getINStudyGroups());
        for (StudyGroup group : studyGroups) {
            if(group.getName().equals(studyGroup)) {
                Toast toast = Toast.makeText(this, "group found:" + group.getId(), Toast.LENGTH_LONG);
                toast.show();
                new UpdateLessonDataTask().execute(group);
            }
        }
        if(studyGroups.size() == 0) {
            Toast toast4 = Toast.makeText(this, "list empty!", Toast.LENGTH_LONG);
            toast4.show();
        }
    }

    public void updateView(View view) {
        //erst klicken wenn Datenbank fertig
        createLessonList();
    }

    private class SetupTimetableDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            taskProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new NetworkUtils().setup(context);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            taskProgressBar.setVisibility(ProgressBar.INVISIBLE);
            Toast taskToast = Toast.makeText(context, "Finished loading", Toast.LENGTH_SHORT);
            taskToast.show();
            if(studyGroup != null)
            createLessonList();
        }
    }

    private class UpdateLessonDataTask extends AsyncTask<StudyGroup, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            taskProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(StudyGroup... groups) {
            new NetworkUtils().getLessons(groups[0], context);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            taskProgressBar.setVisibility(ProgressBar.INVISIBLE);
            Toast taskToast = Toast.makeText(context, "Finished lesson setup", Toast.LENGTH_SHORT);
            taskToast.show();
        }
    }

}
