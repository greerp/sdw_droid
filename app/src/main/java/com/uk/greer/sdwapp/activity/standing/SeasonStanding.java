package com.uk.greer.sdwapp.activity.standing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.inqbarna.tablefixheaders.samples.SimpleTable;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.config.BundleProperty;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;
import com.uk.greer.sdwapp.service.TimeTrialStandingMatrix;

public class SeasonStanding extends ActionBarActivity {

    private static Fragment createMatrixFragment(BundleProperty.COMPETITION compType, int seasonId) {
        TimeTrialEventService trialEventService;


        try {
            trialEventService = TimeTrialEventServiceFactory.getInstance();
        } catch (Exception e) {
            Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
            trialEventService = new TimeTrialEventServiceNull();
        }

        TimeTrialStandingMatrix ttMatrixService = new TimeTrialStandingMatrix(trialEventService);

        String[][] data;
        if (compType== BundleProperty.COMPETITION.HANDICAP)
            data = ttMatrixService.createMatrixForHandicapCompetition(seasonId, 10);
        else
            data = ttMatrixService.createMatrixForScratchCompetition(seasonId, 10);

        return SimpleTable.newInstance(data);
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BundleProperty.COMPETITION compType =
                    (BundleProperty.COMPETITION)getIntent().getSerializableExtra(BundleProperty.TT_COMPETITION);

        int seasonId = getIntent().getIntExtra(BundleProperty.TT_SEASON_ID,0);

        setContentView(R.layout.season_standing);
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTrx = fragMan.beginTransaction();
        fragTrx.add(R.id.standings_container, createMatrixFragment(compType, seasonId));
        fragTrx.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_season_standing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if ( id==android.R.id.home ) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
