package com.uk.greer.sdwapp.persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uk.greer.sdwapp.config.ApplicationProperty;
import com.uk.greer.sdwapp.service.CacheService;

import java.util.Date;

/**
 * Created by greepau on 16/03/2015.
 */
public class LocalDataStore extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Context context;

    public LocalDataStore(Context context) {
        super(context,
                ApplicationProperty.get("LOCALDB","sdw.db"), null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableDefinition.COURSES_TABLE);
        db.execSQL(TableDefinition.EVENTRESULTS_TABLE);
        db.execSQL(TableDefinition.EVENTS_TABLE);
        db.execSQL(TableDefinition.PARTICIPANTS_TABLE);
        // We need to send a trigger to populate the database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



}
