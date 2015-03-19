package com.uk.greer.sdwapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.persist.LocalDataStore;
import com.uk.greer.sdwapp.persist.TableDefinition;

/**
 * Created by greepau on 18/03/2015.
 */
public class CacheService {

    private Context context;

    public CacheService(Context context) {
        this.context = context;
    }


    public boolean cacheCourse(int id, String name, String coursecode, String coursemapurl, String coursenotes, double distance) {

        SQLiteDatabase db=null;
        try {
            db = getWriteableDatabase();

            String[] courseFields = new String[]{
                    TableDefinition.COURSE_ID,
                    TableDefinition.COURSE_NAME,
                    TableDefinition.COURSE_CODE,
                    TableDefinition.COURSE_MAPURL,
                    TableDefinition.COURSE_NOTES,
                    TableDefinition.COURSE_DISTANCE};

            Cursor c = db.query(TableDefinition.COURSE_TABLE,
                    courseFields,
                    "id=?",
                    new String[]{Integer.toString(id)},
                    null, null, null);

            if (c != null) {
                c.moveToFirst();
                if (c.isAfterLast()) {
                    // Insert
                    ContentValues values = new ContentValues();
                    values.put(TableDefinition.COURSE_ID, id);
                    values.put(TableDefinition.COURSE_NAME, name);
                    values.put(TableDefinition.COURSE_CODE, coursecode);
                    values.put(TableDefinition.COURSE_MAPURL, coursemapurl);
                    values.put(TableDefinition.COURSE_NOTES, coursenotes);
                    values.put(TableDefinition.COURSE_DISTANCE, distance);

                    long newRowId;
                    newRowId = db.insert(
                            TableDefinition.COURSE_TABLE,
                            null,
                            values);
                } else {
                    // Check for updates


                }
            }
        }
        finally{
            if ( db!=null){
                if (db.isOpen()) db.close();
            }
        }

        return true;

    }

    private SQLiteDatabase getWriteableDatabase() {
        return ((AppManager) context.getApplicationContext()).getLocalDataStore().getWritableDatabase();
    }

    public boolean cacheEvent() {
        return true;
    }

    public boolean cacheResult() {
        return true;
    }

    public boolean cacheParticipant() {
        return true;
    }


}
