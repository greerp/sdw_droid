package com.uk.greer.sdwapp.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.Participant;
import com.uk.greer.sdwapp.domain.TimeTrial;

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
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
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
        List<TimeTrial> l = getTimeTrialList("id = ?", new String[]{Long.toString(id)}, "eventdate");

        if ( l.size()>0)
            return l.get(0);
        else
            return null;
    }

    @Override
    public List<Participant> getEntries(long ttId) {
        List<Participant> participants = new ArrayList<>();
        SQLiteDatabase readOnlyDb = null;
        Cursor s = null;
        try {
            readOnlyDb = getReadableDatabase();

            String[] fields = new String[]{
                    "id", "username", "firstname","lastname","signondate", "signonmethod", "handicap"};

            s = readOnlyDb.query("v_ttentries",
                    fields,"eventid = ?", new String[]{Long.toString(ttId)},null,null,"signondate");

            if (s.getCount() > 0) {
                s.moveToFirst();
                int eventNo = 0;
                do {
                    Date eventDate = null;
                    try {
                        String d = s.getString(4);
                        eventDate = dateFormat.parse(d);
                    } catch (ParseException e) {
                        Log.e("EXCEPTION","Unable to parse date:"+ s.getString(1));
                    }
                    Participant participant =
                          Participant.newInstance(
                                  s.getInt(0), s.getString(1), s.getString(2),s.getString(3), eventDate);
                    participants.add(participant);
                    s.moveToNext();
                } while (!s.isAfterLast());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeCursor(s);
            closeDB(readOnlyDb);
        }
        return participants;
    }

    @Override
    public int getEntryCount(long ttId) {

        SQLiteDatabase mDb = getReadableDatabase();
        try {
            final SQLiteStatement stmt = mDb
                    .compileStatement("SELECT count(id) FROM entries where eventid="+ ttId);

            return (int) stmt.simpleQueryForLong();
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            if ( mDb!=null)
                closeDB(mDb);
        }
        return 0;
    }


    private List<TimeTrial> getTimeTrialList(String where, String[] selectionArgs, String orderBy) {
        List<TimeTrial> ttList = new ArrayList<>();
        SQLiteDatabase readOnlyDb = null;
        Cursor s = null;
        try {
            readOnlyDb = getReadableDatabase();

            String[] fields = new String[]{
                    "id", "eventdate", "coursename", "coursecode", "distance", "eventname","coursenotes"};

            s = readOnlyDb.query("v_timetrials", fields, where, selectionArgs, null, null, orderBy);

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
                        tt = TimeTrial.newInstance( s.getInt(0), ++eventNo, s.getString(2), eventDate, s.getString(3), true, s.getString(6));
                    else
                        tt = TimeTrial.newInstance( s.getInt(0), ++eventNo, s.getString(5), eventDate, s.getString(3), true, s.getString(6));

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


}
