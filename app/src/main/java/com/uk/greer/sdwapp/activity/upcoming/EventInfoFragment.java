package com.uk.greer.sdwapp.activity.upcoming;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.TimeTrial;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventInfoFragment extends Fragment {
    private static final String TT_ID = "tt_id";
    private final SimpleDateFormat dateFormat;
    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    public static EventInfoFragment newInstance(int ttId){

        EventInfoFragment fragment = new EventInfoFragment();

        Bundle args = new Bundle();
        args.putInt(TT_ID, ttId);
        fragment.setArguments(args);
        return fragment;
    }

    public EventInfoFragment(){
        String dateFormat =
                AppManager.getStringResource(R.string.LONG_DATE_FORMAT, "dd MMM yyyy");
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upcoming_event_info_fragment, container, false);


        Bundle args = this.getArguments();
        if ( args!=null) {
            int ttId = args.getInt(this.TT_ID);

            TimeTrialEventService trialEventService;

            try {
                trialEventService = TimeTrialEventServiceFactory.getInstance();
            } catch (Exception e) {
                Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
                trialEventService = new TimeTrialEventServiceNull();
            }

            TimeTrial tt = trialEventService.getTimeTrial(ttId);
            int entryCount = trialEventService.getEntryCount(ttId);


            if (tt != null)
                showDetails(v, tt, entryCount);
        }
        else {
            Log.e("ERROR", "No arguments, instance may have been created directly an dnot through newInstance");
        }
        return v;
    }

    private int getScreenWidth() {
        //Display display = getWindowManager().getDefaultDisplay();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /*
    @Override

    public Intent getSupportParentActivityIntent() {
        // We return the TT ID that was selected so that when the parent is recreated it
        // can obtain the ID of the event and reinstate the position on the list

        Intent intent=super.getSupportParentActivityIntent();

        //TODO: This may not work
        intent.putExtra("TT_ID", ttId);
        return intent;
    }
    */

    private void showDetails(View v, TimeTrial timeTrial, int entryCount) {

        // Set the width of the button - This is necessary if the date is empty
        int width = getScreenWidth();

        Button registerButton = (Button) v.findViewById(R.id.register_button);
        registerButton.setMaxWidth(width);

        TextView fldEventName = (TextView) v.findViewById(R.id.event_name);
        fldEventName.setText(timeTrial.getName());

        TextView fldCourseDesc = (TextView) v.findViewById(R.id.course_description);
        fldCourseDesc.setText(timeTrial.getNotes());

        TextView fldEventDate = (TextView) v.findViewById(R.id.event_date);

        Date eventDate = timeTrial.getEventDate();
        if ( eventDate!=null)
            fldEventDate.setText(dateFormat.format(eventDate));
        else
            fldEventDate.setText("");

        TextView fldEntryCount = (TextView) v.findViewById(R.id.course_entrants_info);
        fldEntryCount.setText(entryCount + " riders have pre-entered on-line");

    }

}
