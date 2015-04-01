package com.uk.greer.sdwapp.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.TimeTrial;
import com.uk.greer.sdwapp.persist.LocalDataStore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greepau on 17/03/2015.
 */
public class TimeTrialEventServiceCache implements TimeTrialEventService {

    private final Context context;
    private SimpleDateFormat dateFormat;

    public TimeTrialEventServiceCache(Context context) {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        this.context = context;
    }

    private SQLiteDatabase getReadableDatabase() {
        return ((AppManager) context.getApplicationContext()).getLocalDataStore().getReadableDatabase();
    }

    public List<TimeTrial> getUpcomingEvents() {

        String queryDate = dateFormat.format(new Date());
        return getTimeTrialList(
                "eventdate > ?", new String[]{queryDate}, "eventdate");
    }

    public List<TimeTrial> getCompletedEvents() {

        String queryDate = dateFormat.format(new Date());
        return getTimeTrialList(
                "eventdate < ?", new String[]{queryDate}, "eventdate");
    }
    @Override
    public TimeTrial getTimeTrial(long id) {
        List<TimeTrial> l = getTimeTrialList("eventid = ?", new String[]{Long.toString(id)}, "eventdate");

        if ( l.size()>0)
            return l.get(0);
        else
            return null;
    }


    private List<TimeTrial> getTimeTrialList(String where, String[] selectionArgs, String orderBy) {
        List<TimeTrial> ttList = new ArrayList<>();
        SQLiteDatabase readOnlyDb = null;
        Cursor s = null;
        try {
            readOnlyDb = getReadableDatabase();

            s = readOnlyDb.query("v_timetrials", null, where, selectionArgs, null, null, orderBy);

            int i = s.getCount();
            if (i <= 0) {
                Log.i("INITIALIZATION", "Refreshing database ");
            } else {
                s.moveToFirst();
                int eventNo = 0;
                do {
                    Date eventDate = null;
                    try {
                        eventDate = dateFormat.parse(s.getString(1));
                    } catch (ParseException e) {
                        Log.e("EXCEPTION","Unable to parse date:"+ s.getString(1));
                    }

                    TimeTrial tt;

                    if (s.getString(5) == null | s.getString(5).isEmpty())
                        tt = TimeTrial.newInstance( s.getInt(0), ++eventNo, s.getString(2), eventDate, s.getString(3), true);
                    else
                        tt = TimeTrial.newInstance( s.getInt(0), ++eventNo, s.getString(5), eventDate, s.getString(3), true);

                    ttList.add(tt);
                    s.moveToNext();
                } while (!s.isAfterLast());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(s);
            closeDB(readOnlyDb);
        }
        return ttList;
    }

    private void closeDB(SQLiteDatabase readOnlyDb) {
        if (readOnlyDb != null)
            readOnlyDb.close();
    }

    private void closeCursor(Cursor s) {
        if (s != null)
            s.close();
    }

//    @Override
//    public List<TimeTrial> getUpcomingEvents1() {
//
//        final String sql = "select events.id, events.eventdate, courses.name, " +
//                "courses.coursecode, courses.distance, events.name " +
//                "from events join courses on events.courseid=courses.id " +
//                "where events.eventdate > ? order by events.eventdate";
//
//        SQLiteDatabase readOnlyDb = null;
//        Cursor s=null;
//        try {
//            readOnlyDb = getReadableDatabase();
//            List<TimeTrial> ttList = new ArrayList<>();
//
//            String queryDate = dateFormat.format(new Date());
//            s = readOnlyDb.rawQuery(sql, new String[]{queryDate});
//
//            int i = s.getCount();
//            if (i <= 0) {
//                Log.i("INITIALIZATION", "Refreshing database ");
//                //refreshdb();
//            } else {
//                s.moveToFirst();
//                int eventNo = 0;
//                do {
//                    Date eventDate = null;
//                    try {
//                        eventDate = dateFormat.parse(s.getString(1));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    String name=s.getString(5)==null?s.getString(2):s.getString(5);
//                    TimeTrial tt = TimeTrial.newInstance(
//                            s.getInt(0), ++eventNo, name, eventDate, s.getString(3), true);
//                    ttList.add(tt);
//                    s.moveToNext();
//
//                } while (!s.isAfterLast());
//            }
//            return ttList;
//        }
//         catch (Exception e){
//             e.printStackTrace();
//         }
//        finally {
//            closeCursor(s);
//
//            closeDB(readOnlyDb);
//        }
//        return new ArrayList<>();
//    }


//    @Override
//    public TimeTrial getTimeTrial(long id) {
//        final String sql = "select events.id, events.eventdate, courses.name, " +
//                "courses.coursecode, courses.distance " +
//                "from events join courses on events.courseid=courses.id " +
//                "where events.id = ?";
//
//        SQLiteDatabase readOnlyDb=null;
//        Cursor s = null;
//        try {
//            readOnlyDb = getReadableDatabase();
//            s = readOnlyDb.rawQuery(sql, new String[]{Long.toString(id)});
//            int i = s.getCount();
//            if (i <= 0) {
//                return null;
//            } else {
//                s.moveToFirst();
//                int eventNo = 0;
//                Date eventDate = null;
//                try {
//                    eventDate = dateFormat.parse(s.getString(1));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                TimeTrial tt = TimeTrial.newInstance(
//                        s.getInt(0), ++eventNo, s.getString(2), eventDate, s.getString(3), true);
//                return tt;
//            }
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//        }
//        finally {
//            closeCursor(s);
//            closeDB(readOnlyDb);
//        }
//        return null;
//    }

}
