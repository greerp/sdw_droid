package com.uk.greer.sdwapp;

import android.app.Application;
import android.os.StrictMode;

import com.uk.greer.sdwapp.persist.LocalDataStore;

/**
 * Created by greepau on 18/03/2015.
 */
public class AppManager extends Application {

    private LocalDataStore localDataStore;

    public LocalDataStore getLocalDataStore(){
        if ( localDataStore==null){
            localDataStore = new LocalDataStore(this);
        }

        return localDataStore;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        setUpStrictMode();
    }

    private void setUpStrictMode() {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
