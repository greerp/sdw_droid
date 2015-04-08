package com.uk.greer.sdwapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.StrictMode;

import com.uk.greer.sdwapp.persist.LocalDataStore;

/**
 * Created by greepau on 18/03/2015.
 */
public class AppManager extends Application {

    private LocalDataStore localDataStore;
    private static Context ctx;

    public LocalDataStore getLocalDataStore(){
        if ( localDataStore==null){
            localDataStore = new LocalDataStore(this);
        }
        return localDataStore;
    }

    public static Context getContext(){
        return ctx;
    }

    public static String getStringResource(int resourceId, String defaultValue){

        if ( ctx!=null){
            String result = ctx.getString(resourceId);
            if (result!=null)
                return result;
        }
        return defaultValue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpStrictMode();
        ctx = getApplicationContext();
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
