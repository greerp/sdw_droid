package com.uk.greer.sdwapp.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uk.greer.sdwapp.config.ApplicationProperty;

/**
 * Created by greepau on 16/03/2015.
 */
public class LocalDataStore extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Context context;

    public LocalDataStore(Context context) {

        super(context,
                ApplicationProperty.get("LOCALDB", "sdw.db"), null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // TODO: 27-4-15 - Create the database in here from the assets folder

        Log.i("DATABASE", "In OnCreate");
//        db.execSQL(TableDefinition.CONFIG_TABLE);
//        db.execSQL(TableDefinition.COURSES_TABLE);
//        db.execSQL(TableDefinition.EVENTS_TABLE);
//        db.execSQL(TableDefinition.ENTRIES_TABLE);
//        db.execSQL(TableDefinition.USERS_TABLE);
//        db.execSQL(TableDefinition.TIMETRIALS_VIEW);
//        db.execSQL(TableDefinition.ENTRIES_VIEW);
        // We need to send a trigger to populate the database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



}
