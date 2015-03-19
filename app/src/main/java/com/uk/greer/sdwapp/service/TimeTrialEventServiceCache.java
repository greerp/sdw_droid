package com.uk.greer.sdwapp.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.domain.TimeTrial;
import com.uk.greer.sdwapp.persist.LocalDataStore;

import java.util.List;

/**
 * Created by greepau on 17/03/2015.
 */
public class TimeTrialEventServiceCache implements TimeTrialEventService {

    private final Context context;

    public TimeTrialEventServiceCache(Context context) {

        this.context = context;
    }

    private SQLiteDatabase getReadableDatabase() {
        return ((AppManager) context.getApplicationContext()).getLocalDataStore().getReadableDatabase();
    }


    private void refreshdb() {
        // Block until weve got the data
        CacheService cs = new CacheService(context);
        cs.cacheCourse(1,"Sleeches/Mayfield","GS878",null,"Undulating",9.88);
        cs.cacheCourse(2, "Ladies Mile", "GS898", null, "3 laps of hilly loop, quiet roads, used for come and try it events, feared by many but loved by a few", 11.5);
        cs.cacheCourse(3, "Sleeches/Wadhurst", "GS879", null, "Starts the same place as Sleeches/Mayfield but flattens off before going up a 2 mile drag", 11.5);
    }


    @Override
    public List<TimeTrial> getUpcomingEvents() {

        SQLiteDatabase readOnlyDb = getReadableDatabase();
        Cursor s = readOnlyDb.rawQuery("select name from courses",null);
        if (s.isAfterLast()){
            Log.i("INITIALIZATION","Refreshing database ");
            refreshdb();
        }


        return null;
    }

    @Override
    public TimeTrial getTimeTrial(long id) {
        //return temp.getTimeTrial(id);
        return null;
    }

}
