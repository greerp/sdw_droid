package com.uk.greer.sdwapp.service;

import android.content.Context;
import android.util.SparseArray;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.Result;
import com.uk.greer.sdwapp.domain.Standing;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by greepau on 02/06/2015.
 */
public class TimeTrialStandingMatrix {

    private final TimeTrialEventService timeTrialEventService;

    public TimeTrialStandingMatrix(TimeTrialEventService timeTrialEventService) {
        this.timeTrialEventService = timeTrialEventService;
    }

    public String[][] createMatrixForScratchCompetition(long seriesId, int topXResults) {
        String dataFormat = AppManager.getStringResource(R.string.stdtable_dateprefix,"dd-MMM-");
        SimpleDateFormat df =  new SimpleDateFormat(dataFormat);

        List<Standing> standings = getScratchCompetitionStandings(seriesId, topXResults);
        List<TimeTrial> seriesEvents = getCompletedTimeTrialsForSeries(seriesId);
        List<Result> seriesResults = timeTrialEventService.getSeriesResults(seriesId);

        SparseArray userLookup = new SparseArray(standings.size());
        SparseArray eventLookup = new SparseArray(seriesEvents.size());

        int rowCount=standings.size()+1;
        int colCount=seriesEvents.size()+4;

        int enteredColumnIdx = colCount-3;
        int best10ColumnIdx = colCount-2;
        int totPtsColumnIdx = colCount-1;

        String[][] matrix = new String[rowCount][colCount];
        matrix[0][0]=AppManager.getStringResource(R.string.stdtable_participant_heading,"Name");
        matrix[0][enteredColumnIdx]=AppManager.getStringResource(R.string.stdtable_entrycount_heading,"Entries");
        matrix[0][best10ColumnIdx]=AppManager.getStringResource(R.string.stdtable_best_heading,"Best 10");
        matrix[0][totPtsColumnIdx]=AppManager.getStringResource(R.string.stdtable_total_heading,"Total");

        // First Row containing event names
        for ( int n=0;n<seriesEvents.size();n++){
            TimeTrial tt=seriesEvents.get(n);
            matrix[0][n+1]=df.format(tt.getEventDate())+tt.getName();
            eventLookup.put(tt.getId(),n+1);
        }

        // First Column containing participant name
        for ( int n=0;n<standings.size();n++){
            Standing standing=standings.get(n);
            matrix[n+1][0]=standing.getFirstName()+" "+standing.getLastName();
            userLookup.put(standing.getUserId(),n+1);
            matrix[n+1][enteredColumnIdx]=String.valueOf(standing.getEntered());
            matrix[n+1][best10ColumnIdx]=String.valueOf(standing.getScrpts());
            matrix[n+1][totPtsColumnIdx]=String.valueOf(standing.getTotHcpPts());
        }

        for (Result result : seriesResults){
            // Get array indexes
            int r = (int) userLookup.get(result.getUserId());
            int c = (int) eventLookup.get(result.getEventId());
            matrix[r][c]= String.valueOf(result.getHcppts());
        }

        return matrix;

    }

    public String[][] createMatrixForHandicapCompetition(long seriesId, int topXResults){

        String dataFormat = AppManager.getStringResource(R.string.stdtable_dateprefix,"dd-MMM-");
        SimpleDateFormat df =  new SimpleDateFormat(dataFormat);

        List<Standing> standings = getHandicapCompetitionStandings(seriesId, topXResults);
        List<TimeTrial> seriesEvents = getCompletedTimeTrialsForSeries(seriesId);
        List<Result> seriesResults = timeTrialEventService.getSeriesResults(seriesId);

        SparseArray userLookup = new SparseArray(standings.size());
        SparseArray eventLookup = new SparseArray(seriesEvents.size());

        int rowCount=standings.size()+1;
        int colCount=seriesEvents.size()+4;

        int enteredColumnIdx = colCount-3;
        int best10ColumnIdx = colCount-2;
        int totPtsColumnIdx = colCount-1;

        String[][] matrix = new String[rowCount][colCount];
        matrix[0][0]=AppManager.getStringResource(R.string.stdtable_participant_heading,"Name");
        matrix[0][enteredColumnIdx]=AppManager.getStringResource(R.string.stdtable_entrycount_heading,"Entries");
        matrix[0][best10ColumnIdx]=AppManager.getStringResource(R.string.stdtable_best_heading,"Best 10");
        matrix[0][totPtsColumnIdx]=AppManager.getStringResource(R.string.stdtable_total_heading,"Total");

        // First Row containing event names
        for ( int n=0;n<seriesEvents.size();n++){
            TimeTrial tt=seriesEvents.get(n);
            matrix[0][n+1]=df.format(tt.getEventDate())+tt.getName();
            eventLookup.put(tt.getId(),n+1);
        }

        // First Column containing participant name
        for ( int n=0;n<standings.size();n++){
            Standing standing=standings.get(n);
            matrix[n+1][0]=standing.getFirstName()+" "+standing.getLastName();
            userLookup.put(standing.getUserId(),n+1);
            matrix[n+1][enteredColumnIdx]=String.valueOf(standing.getEntered());
            matrix[n+1][best10ColumnIdx]=String.valueOf(standing.getHcppts());
            matrix[n+1][totPtsColumnIdx]=String.valueOf(standing.getTotHcpPts());
        }

        for (Result result : seriesResults){
            // Get array indexes
            int r = (int) userLookup.get(result.getUserId());
            int c = (int) eventLookup.get(result.getEventId());
            matrix[r][c]= String.valueOf(result.getHcppts());
        }

        return matrix;
    }

    private List<TimeTrial> getCompletedTimeTrialsForSeries(long seriesId) {
        Date now = new Date();
        List<TimeTrial> seriesEvents = timeTrialEventService.getSeriesEvents(seriesId);
        Iterator<TimeTrial> itr = seriesEvents.iterator();
        while(itr.hasNext()){
            TimeTrial tt = itr.next();
            if ( tt!=null && tt.getEventDate().after(now)){
                itr.remove();
            }
        }
        return seriesEvents;
    }

    private List<Standing> getScratchCompetitionStandings(long seriesId, int topXResults) {
        List<Standing> standings =
                timeTrialEventService.getStandings(seriesId, topXResults, topXResults);
        Collections.sort(standings, new Comparator<Standing>() {
            @Override
            public int compare(Standing lhs, Standing rhs) {
                int h1 = lhs.getScrpts();
                int h2 = rhs.getScrpts();
                if (h1 < h2)
                    return 1;
                if (h1 > h2)
                    return -1;
                return 0;
            }
        });
        return standings;

    }
    private List<Standing> getHandicapCompetitionStandings(long seriesId, int topXResults) {
        List<Standing> standings =
                timeTrialEventService.getStandings(seriesId, topXResults, topXResults);

        Collections.sort(standings, new Comparator<Standing>() {
            @Override
            public int compare(Standing lhs, Standing rhs) {
                int h1 = lhs.getHcppts();
                int h2 = rhs.getHcppts();
                if (h1 < h2)
                    return 1;
                if (h1 > h2)
                    return -1;
                return 0;
            }
        });
        return standings;
    }
}
