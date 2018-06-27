package com.project.appproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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

import com.project.appproject.database.Lesson;
import com.project.appproject.database.StudyGroup;
import com.project.appproject.database.TimetableDatabase;
import com.project.appproject.utilities.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.project.appproject.R.color.colorTimetableBackground;



public class TimetableActivity extends AppCompatActivity {
    private String studyGroup;
    SharedPreferences prefs;
    private Context context;
    private ProgressBar taskProgressBar;
    private boolean lessonsLoaded;
    private boolean databaseLoaded;

    private static final String PREFS_KEY_STUDYGROUP = "currentStudyGroup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = findViewById(R.id.timetable_toolbar);
        toolbar.setTitle(R.string.timetable);
        setSupportActionBar(toolbar);
        taskProgressBar = findViewById(R.id.taskProgressBar);
        context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        studyGroup = prefs.getString(PREFS_KEY_STUDYGROUP, null);
        if(studyGroup == null) {
            lessonsLoaded = false;
            databaseLoaded = false;
            new SetupTimetableDataTask().execute();
            Dialog dialog = onCreateDialog(savedInstanceState);
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawLessons();
        updateStudyGroup();
    }

    private void updateStudyGroup() {
        TextView pickedStudyGroupText = findViewById(R.id.textViewStudyGroup);
        if(studyGroup != null && pickedStudyGroupText != null) {
            pickedStudyGroupText.setText(studyGroup);
        }
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
                return super.onOptionsItemSelected(item);
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] groups = new String[] {"I1[A-K]", "I1[L-Z]", "I2", "I3", "I4", "I5", "I6", "I7"};
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
                                if(databaseLoaded && !lessonsLoaded) {
                                    createLessonList();
                                }
                        }});

        return builder.create();
    }

    public void createLessonList() {
        ArrayList<StudyGroup> studyGroups =
                new ArrayList<>(TimetableDatabase.getInstance(this).studyGroupDao().getINStudyGroups());
        for (StudyGroup group : studyGroups) {
            if(group.getName().equals(studyGroup)) {
                new UpdateLessonDataTask().execute(group);
            }
        }
        if(studyGroups.size() == 0) {
            Toast toast4 = Toast.makeText(this, "list empty!", Toast.LENGTH_LONG);
            toast4.show();
        }
    }

    private void drawLessons() {
        ArrayList<Lesson> lessons =
                new ArrayList<>(TimetableDatabase.getInstance(this).lessonDao().getAll());

        for (Lesson lesson : lessons) {
            TextView lessonTextView = getTextView(lesson, false);
            if(lessonTextView != null) {
                //set subjects
                boolean useShortNames = prefs.getBoolean(TimetableSettingsActivity.PREFS_KEY_SHORT_NAMES, true);
                if (useShortNames) {
                    setSubjectName(lesson, lessonTextView);
                } else {
                    setSubjectLongName(lesson, lessonTextView);
                }
                //set background color
                lessonTextView.setBackgroundColor(getResources().getColor(colorTimetableBackground));
            }
            lessonTextView = getTextView(lesson, true);
            if(lessonTextView != null) {
                //set rooms
                boolean hideRooms = prefs.getBoolean(TimetableSettingsActivity.PREFS_KEY_HIDE_ROOMS, false);
                if(hideRooms) {
                    lessonTextView.setText("");
                } else {
                    setRoom(lesson, lessonTextView);
                }
                //set background color
                lessonTextView.setBackgroundColor(getResources().getColor(colorTimetableBackground));
            }
        }
    }

    private void setRoom(Lesson lesson, TextView lessonTextView) {
        String room = TimetableDatabase.getInstance(this).roomDao().getRoomById(lesson.getRoomID());
        lessonTextView.setText(room);
    }

    private void setSubjectName(Lesson lesson, TextView lessonTextView) {
        String subjectName = TimetableDatabase.getInstance(this).subjectDao().getSubjectNameById(lesson.getSubjectID());
        String name = subjectName.substring(3);
        lessonTextView.setText(name);
    }

    private void setSubjectLongName(Lesson lesson, TextView lessonTextView) {
        String subjectName = TimetableDatabase.getInstance(this).subjectDao().getSubjectLongNameById(lesson.getSubjectID());
        lessonTextView.setText(subjectName);
    }

    private TextView getTextView(Lesson lesson, boolean isRoomTextView) {
        String weekday = getWeekday(lesson);
        String lessonTime = getLessonTime(lesson);
        if(weekday == null || lessonTime == null) {
            return null;
        }
        String stringId = weekday.concat(lessonTime);
        if(isRoomTextView) {
            stringId = stringId.concat("Room");
        }
        int id  = getResources().getIdentifier(stringId,"id", getPackageName());
        TextView lessonTextView = findViewById(id);
        return lessonTextView;
    }

    private String getLessonTime(Lesson lesson) {
        String startTime = lesson.getStartTime();
        if(startTime.startsWith("8")) {
            return "Lesson1";
        }
        if(startTime.startsWith("10")) {
            return "Lesson2";
        }
        if(startTime.startsWith("11")) {
            return "Lesson3";
        }
        if(startTime.startsWith("13")) {
            return "Lesson4";
        }
        if(startTime.startsWith("15")) {
            return "Lesson5";
        }
        if(startTime.startsWith("17")) {
            return "Lesson6";
        }
        if(startTime.startsWith("18")) {
            return "Lesson7";
        }
        return null;
    }

    @NonNull
    private String getWeekday(Lesson lesson) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = dateFormat.parse(lesson.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayofWeek) {
            case Calendar.MONDAY:
                return "monday";
            case Calendar.TUESDAY:
                return  "tuesday";
            case Calendar.WEDNESDAY:
                return  "wednesday";
            case Calendar.THURSDAY:
                return  "thursday";
            case Calendar.FRIDAY:
                return "friday";
        }
        return null;
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            taskProgressBar.setVisibility(ProgressBar.INVISIBLE);
            Toast taskToast = Toast.makeText(context, "Finished loading database", Toast.LENGTH_SHORT);
            taskToast.show();
            databaseLoaded = true;
            if(studyGroup != null) {
                createLessonList();
            }
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
            drawLessons();
            lessonsLoaded = true;
        }
    }

}
