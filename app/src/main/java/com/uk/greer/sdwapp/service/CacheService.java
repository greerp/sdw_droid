package com.uk.greer.sdwapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.persist.TableDefinition;

/**
 * Created by greepau on 18/03/2015.
 */
public class CacheService {

    private Context context;

//    private void refreshdb() {
//        // Block until weve got the data
//        CacheService cs = new CacheService(context);
//        cs.cacheCourse(1, "Sleeches/Mayfield", "GS878", null, "Undulating", 9.88);
//        cs.cacheCourse(2, "Ladies Mile", "GS898", null, "3 laps of hilly loop, quiet roads, used for come and try it events, feared by many but loved by a few", 11.5);
//        cs.cacheCourse(3, "Sleeches/Wadhurst", "GS879", null, "Starts the same place as Sleeches/Mayfield but flattens off before going up a 2 mile drag", 11.5);
//    }


    public CacheService(Context context) {
        this.context = context;
    }


    public boolean cacheCourse(int id, String name, String coursecode, String coursemapurl, String coursenotes, double distance) {

        SQLiteDatabase db = null;
        try {
            db = getWriteableDatabase();

            String[] courseFields = new String[]{
                    TableDefinition.COURSE_ID,
                    TableDefinition.COURSE_NAME,
                    TableDefinition.COURSE_CODE,
                    TableDefinition.COURSE_MAPURL,
                    TableDefinition.COURSE_NOTES,
                    TableDefinition.COURSE_DISTANCE};

            Cursor c = db.query(TableDefinition.COURSE,
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
                            TableDefinition.COURSE,
                            null,
                            values);
                } else {
                    // Check for updates


                }
            }
        } finally {
            if (db != null) {
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
