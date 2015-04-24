package com.uk.greer.sdwapp.activity.upcoming;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.Participant;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by greepau on 13/04/2015.
 */
public class EventEntriesFragment extends Fragment {

    private static final String TT_ID = "tt_id";
    private final SimpleDateFormat dateFormat;

    public EventEntriesFragment() {
        String dateFormat =
                AppManager.getStringResource(R.string.SHORT_DATE_FORMAT, "dd MMM yyyy");
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

    public static EventEntriesFragment newInstance(int ttId){

        EventEntriesFragment fragment = new EventEntriesFragment();

        Bundle args = new Bundle();
        args.putInt(TT_ID, ttId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upcoming_event_entries_fragment, container, false);

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

            List<Participant> entries = trialEventService.getEntries(ttId);

            if (entries != null)
                showEntries(v, entries);
        }
        else {
            Log.e("ERROR", "No arguments, instance may have been created directly an dnot through newInstance");
        }
        return v;
    }


    private void showEntries(View v,  List<Participant> entries){
        /****************** Display Entrees *********************/
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TableLayout table = (TableLayout) v.findViewById(R.id.entreesTableLayout);

        if ( entries != null && entries.size()>0) {
            table.removeViewAt(0);
            for (Participant entrant : entries) {
                View rowView = inflater.inflate(R.layout.fragment_event_entree, null, false);
                TextView userName = (TextView) rowView.findViewById(R.id.entreeUserName);
                TextView date = (TextView) rowView.findViewById(R.id.entreeDate);
                userName.setText(entrant.getUserName());
                date.setText(dateFormat.format(entrant.getSignUpDate()));
                table.addView(rowView);
            }
        }
    }
}
