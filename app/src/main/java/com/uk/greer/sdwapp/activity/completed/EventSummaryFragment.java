package com.uk.greer.sdwapp.activity.completed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.config.BundleProperty;
import com.uk.greer.sdwapp.domain.Result;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.util.List;

public class EventSummaryFragment extends Fragment {

    private int eventId;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CompletedEventSummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventSummaryFragment newInstance(int eventId) {
        EventSummaryFragment fragment = new EventSummaryFragment();

        Bundle args = new Bundle();
        args.putInt(BundleProperty.TT_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getInt(BundleProperty.TT_EVENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.completed_event_summary_fragment, container, false);

        Bundle args = this.getArguments();
        if ( args!=null) {
            int ttId = args.getInt(BundleProperty.TT_EVENT_ID);

            TimeTrialEventService trialEventService;

            try {
                trialEventService = TimeTrialEventServiceFactory.getInstance();
            } catch (Exception e) {
                Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
                trialEventService = new TimeTrialEventServiceNull();
            }

            List<Result> results = trialEventService.getEventNonFinishers(ttId);

            if (results != null)
                showDetails(v, results);
        }
        else {
            Log.e("ERROR", "No arguments, instance may have been created directly and not through newInstance");
        }
        return v;


    }

    private void showDetails(View v, List<Result> results) {

        GridLayout grid = (GridLayout) v.findViewById(R.id.dnsRiders);
        for (Result r :results) {
            grid.addView(getDnsView(v,r.getFirstName()+ " " + r.getLastName(),r.getStatus()));
        }
    }


    private View getDnsView(View parent, String riderName, String reason) {

        LayoutInflater inflater = AppManager.getLayoutInflater();

        View v = inflater.inflate(
                R.layout.completed_event_sumary_dns_entry, null);
        ((TextView)v.findViewById(R.id.dnsRiderName)).setText(riderName);
        ((TextView)v.findViewById(R.id.dnsGivenReason)).setText(reason);
        return v;
    }
}
