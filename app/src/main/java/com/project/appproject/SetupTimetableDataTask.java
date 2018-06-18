package com.project.appproject;

import android.content.Context;
import android.os.AsyncTask;

import com.project.appproject.utilities.NetworkUtils;

public class SetupTimetableDataTask extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
        new NetworkUtils().setup(contexts[0]);
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        System.out.println(progress[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        System.out.println("fertig");
        super.onPostExecute(aVoid);
    }
}
