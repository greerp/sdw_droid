package com.uk.greer.sdwapp.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.domain.DataAccess;
import com.uk.greer.sdwapp.domain.Entry;
import com.uk.greer.sdwapp.domain.Series;
import com.uk.greer.sdwapp.domain.Standing;
import com.uk.greer.sdwapp.domain.TimeTrial;

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

    private final String[] Series_Fields = new String[]{ "id", "name", "seriesstart", "seriesend"};
    private final String[] Standing_fields = new String[]{
            "userid", "username","firstname", "lastname", "scrpts","hcppts","entered","fin","dns","dnf","seriesid"};


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
    public List<Entry> getEntries(long ttId) {
        List<Entry> entries = new ArrayList<>();
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
                do {
                    Entry entry =
                          Entry.newInstance(
                                  s.getInt(0), s.getString(1), s.getString(2), s.getString(3),
                                  convertSqlStrToDate(s.getString(4)));
                    entries.add(entry);
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
        return entries;
    }

    private Date convertSqlStrToDate(String d) {
        Date eventDate = null;
        try {

            eventDate = dateFormat.parse(d);
        } catch (ParseException e) {
            Log.e("EXCEPTION", "Unable to parse date:" + d);
        }
        return eventDate;
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


    private List<Series> getSeries() {
        DataSetService ds = new DataSetService<Series>("series", Series_Fields, null, null, null, null, "id") {
            @Override
            void addDataItem(Cursor s, List<Series> list) {
                list.add(Series.newInstance(s.getInt(0), s.getString(1),
                         convertSqlStrToDate(s.getString(3)), convertSqlStrToDate(s.getString(3))));
            }
        };
        List series = ds.execute();
        return series;
    }

    @Override
    public List<Standing> getStandings(final long seriesId) {
        final List<Series>seriesList = getSeries();
        DataSetService ds = new DataSetService<Standing>("v_ttstandings", Standing_fields,"seriesid=?",
                new String[]{String.valueOf(seriesId)},null,null,"scrpts") {
            @Override
            void addDataItem(Cursor s, List<Standing> list) {

                try {
                    Series series = DataAccess.findById(s.getInt(10), seriesList);
                    Standing standing =
                            Standing.newInstance(s.getInt(0), s.getString(1), s.getString(2), s.getString(3),
                                    s.getInt(4), s.getInt(5), s.getInt(6), s.getInt(7), s.getInt(8),
                                    s.getInt(9), series);
                    list.add(standing);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        List<Standing> series = ds.execute();
        return series;
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


    private abstract class DataSetService<T> {

        private String table;
        private String[] fields;
        private String selection;
        private final String[] selectionArgs;
        private final String groupBy;
        private final String having;
        private final String orderBy;

        public DataSetService(String table, String[] fields, String selection,
                              String[] selectionArgs, String groupBy, String having, String orderBy){
            this.table = table;

            this.fields = fields;
            this.selection = selection;
            this.selectionArgs = selectionArgs;
            this.groupBy = groupBy;
            this.having = having;
            this.orderBy = orderBy;
        }

        abstract void addDataItem(Cursor s, List<T> list);

        private List<T> execute() {
            List<T> series = new ArrayList<T>();
            SQLiteDatabase readOnlyDb = null;
            Cursor s = null;

            try {
                readOnlyDb = getReadableDatabase();
                s = readOnlyDb.query(table, fields, selection, selectionArgs, groupBy, having, orderBy);

                if (s.getCount() > 0) {
                    s.moveToFirst();
                    do {
                        addDataItem(s, series);
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
            return series;
        }
    }



}
