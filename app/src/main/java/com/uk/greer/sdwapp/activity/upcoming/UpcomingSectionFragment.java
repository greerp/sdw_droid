package com.uk.greer.sdwapp.activity.upcoming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

/**
 * Sets up the list view of events
 */
public class UpcomingSectionFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static UpcomingRefreshFragment newInstance( String param1, String param2) {


        UpcomingRefreshFragment fragment = new UpcomingRefreshFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TimeTrial Evenst List
        TimeTrialEventService timeTrialEventService;
        try {
            timeTrialEventService = TimeTrialEventServiceFactory.getInstance();
        }
        catch (Exception e){
            Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
            timeTrialEventService = new TimeTrialEventServiceNull();
        }

        // Create the list adapter that takes the list and populates the list items
        UpcomingListAdapter upcomingListAdapter = new UpcomingListAdapter(
                this.getActivity(),
                timeTrialEventService.getUpcomingEvents());


        // Get the list referenced by this fragment
        ListView upcomingListView = (ListView)inflater.inflate(
                R.layout.fragment_upcoming_section,
                container,
                false);

        // Set the listView to use the adapter
        upcomingListView.setAdapter(upcomingListAdapter);

        // Create delegate that handles clicking on the list event
        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UpcomingEvent.class);
                long eventId = view.getId();
                intent.putExtra("TT_ID",eventId );
                getActivity().startActivity(intent);
            }
        });


        return upcomingListView;
    }
}
