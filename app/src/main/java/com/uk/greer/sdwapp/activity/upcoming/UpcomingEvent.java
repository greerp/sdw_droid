package com.uk.greer.sdwapp.activity.upcoming;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.Participant;
import com.uk.greer.sdwapp.domain.TimeTrial;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Displays the details for a specific TT event
 */
public class UpcomingEvent extends ActionBarActivity {

    private final SimpleDateFormat dateFormat;
    private long ttId;

    public UpcomingEvent(){

        String dateFormat =
                AppManager.getStringResource(R.string.LONG_DATE_FORMAT, "dd MMM yyyy");
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_event);

        // Show Back Button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Retrieve TT using ID passed in to activity
        ttId = getIntent().getExtras().getLong("TT_ID", -1);

        TimeTrialEventService trialEventService;

        try {
            trialEventService = TimeTrialEventServiceFactory.getInstance();
        } catch (Exception e) {
            Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
            trialEventService = new TimeTrialEventServiceNull();
        }

        TimeTrial tt = trialEventService.getTimeTrial(ttId);
        List<Participant> entrees = trialEventService.getEntrees(ttId);

        if (tt != null)
            showDetails(tt, entrees);
    }

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        // We return the TT ID that was selected so that when the parent is recreated it
        // can obtain the ID of the event and reinstate the position on the list
        Intent intent=super.getSupportParentActivityIntent();
        intent.putExtra("TT_ID", ttId);
        return intent;
    }

    private void showDetails(TimeTrial timeTrial, List<Participant> entrees) {

        // Set the width of the button - This is necessary if the date is empty
        int width = getScreenWidth();
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setMaxWidth(width);

        TextView fldEventName = (TextView) findViewById(R.id.event_name);
        fldEventName.setText(timeTrial.getName());

        TextView fldCourseDesc = (TextView) findViewById(R.id.course_description);
        fldCourseDesc.setText(timeTrial.getNotes());

        TextView fldEventDate = (TextView) findViewById(R.id.event_date);

        Date eventDate = timeTrial.getEventDate();
        if ( eventDate!=null)
            fldEventDate.setText(dateFormat.format(eventDate));
        else
            fldEventDate.setText("");

        /****************** Display Entrees *********************/
        LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TableLayout table = (TableLayout) findViewById(R.id.entreesTableLayout);

        if ( entrees != null ) {
            table.removeViewAt(1);
            for (Participant entrant : entrees) {
                View rowView = inflater.inflate(R.layout.fragment_event_entree, null, false);
                TextView userName = (TextView) rowView.findViewById(R.id.entreeUserName);
                TextView date = (TextView) rowView.findViewById(R.id.entreeDate);
                userName.setText(entrant.getUserName());
                date.setText(dateFormat.format(entrant.getSignUpDate()));
                table.addView(rowView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upcoming_event, menu);
        return super.onCreateOptionsMenu(menu);
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
