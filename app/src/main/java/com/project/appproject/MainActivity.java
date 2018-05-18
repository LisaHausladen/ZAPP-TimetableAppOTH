package com.project.appproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


    }

    public void openOTHHomepage(View view) {
        Uri uri = Uri.parse("https://www.oth-regensburg.de");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void openGRIPS(View view) {
        Uri uri = Uri.parse("https://elearning.uni-regensburg.de");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void openWebmail(View view) {
        Uri uri = Uri.parse("https://exchange.hs-regensburg.de");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void openQIS(View view) {
        Uri uri = Uri.parse("https://qis.oth-regensburg.de");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void openFSIM(View view) {
        Uri uri = Uri.parse("https://www.fsim-ev.de");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void openModules(View view) {
        Uri uri = Uri.parse("https://kurse.oth-regensburg.de/kursbelegung");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void openNoticeBoard(View view) {
        Uri uri = Uri.parse("https://www.oth-regensburg.de/fakultaeten/informatik-und-mathematik/schwarzes-brett.html");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    public void goToTimetableActivity(View view) {
        Intent intent = new Intent(this, TimetableActivity.class);
        startActivity(intent);
    }

    public void goToCalendarActivity(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
