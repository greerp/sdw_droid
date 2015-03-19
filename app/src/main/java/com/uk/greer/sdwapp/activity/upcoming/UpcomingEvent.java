package com.uk.greer.sdwapp.activity.upcoming;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.TimeTrial;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceLocal;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

public class UpcomingEvent extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_event);


        // Show Back Button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Retrieve TT using ID passed in to activity
        long id = getIntent().getExtras().getLong("TT_ID", -1);


        TimeTrialEventService trialEventService;

        try {
            trialEventService = TimeTrialEventServiceFactory.getInstance();
        }
        catch (Exception e){
            Log.e("EXCEPTION","Unable to create Time Trial Service, using default");
            trialEventService = new TimeTrialEventServiceNull();
        }

        TimeTrial tt = trialEventService.getTimeTrial(id);
        if ( tt!=null)
            showDetails(tt);
    }

    private void showDetails(TimeTrial timeTrial) {
        //R.layout

        TextView eventName = (TextView) findViewById(R.id.event_name);
        eventName.setText(timeTrial.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upcoming_event, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
