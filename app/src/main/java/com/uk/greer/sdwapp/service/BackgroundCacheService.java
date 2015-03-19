package com.uk.greer.sdwapp.service;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by greepau on 19/03/2015.
 */
public class BackgroundCacheService extends AsyncTask<URL, Integer, Long> {
    @Override
    protected Long doInBackground(URL... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
