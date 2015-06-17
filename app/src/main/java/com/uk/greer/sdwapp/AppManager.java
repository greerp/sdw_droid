package com.uk.greer.sdwapp;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;

import com.uk.greer.sdwapp.persist.DatabaseHelper;
import com.uk.greer.sdwapp.persist.LocalDataStore;

import java.io.IOException;

/**
 * Created by greepau on 18/03/2015.
 */
public class AppManager extends Application {

    private SQLiteOpenHelper localDataStore;
    private static Context ctx;

    public SQLiteOpenHelper getLocalDataStore(){
        if ( localDataStore==null){

            DatabaseHelper dbh = new DatabaseHelper(this);
            try {
                dbh.createDataBase();
                localDataStore = new LocalDataStore(this);

                //localDataStore = dbh;
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    public static void ShowMessageBox(Context context, String message){
        new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
