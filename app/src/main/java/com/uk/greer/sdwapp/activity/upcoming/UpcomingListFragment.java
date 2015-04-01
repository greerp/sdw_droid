package com.uk.greer.sdwapp.activity.upcoming;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.uk.greer.sdwapp.OnDataReady;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.service.CacheCoordinator;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

/**
 * Sets up the list view of events
 */
public class UpcomingListFragment extends Fragment implements OnDataReady {

    public static final String ARG_SECTION_NUMBER = "section_number";

    private static final String ARG_PARAM1 = "tabPosition";
    private static final String ARG_PARAM2 = "tabFunction";
    private int listItemselected;

    public static UpcomingListFragment newInstance(int tabPosition, String tabFunction) {

        Log.i("INFO", "Creating newInstance of: " + tabFunction);
        UpcomingListFragment fragment = new UpcomingListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, tabPosition);
        args.putString(ARG_PARAM2, tabFunction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FrameLayout fl = (FrameLayout) inflater.inflate(
                R.layout.fragment_upcoming_list,
                container,
                false);

        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("events");
        if ( dataReady) {
            showListControl(fl);
            showList(fl);
        }
        else {
            showProgressControls(fl);
        }

        return fl;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        ListView listView = (ListView) this.getView().findViewById(R.id.eventListView);

        outState.putInt("POSITION", listView.getFirstVisiblePosition());
        //TODO: Get the last item clicked
        int i = outState.getInt("POSITION");
        super.onSaveInstanceState(outState);
        Log.i("INFO","UpcomingListFragment.OnSaveInstanceStatePosition:" + Integer.toString(i));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt("POSITION");
            ListView listView = (ListView) this.getView().findViewById(R.id.eventListView);
            if ( position > 0 ){
                //listView.scrollListBy();
                listView.setSelection(position);
            }
        }
    }

    private void showProgressControls(FrameLayout fl) {
        fl.findViewById(R.id.waitingProgressBar).setVisibility(View.VISIBLE);
        fl.findViewById(R.id.waitingMessage).setVisibility(View.VISIBLE);
        fl.findViewById(R.id.eventListView).setVisibility(View.INVISIBLE);
    }

    private void showListControl(FrameLayout fl) {
        fl.findViewById(R.id.waitingProgressBar).setVisibility(View.GONE);
        fl.findViewById(R.id.waitingMessage).setVisibility(View.GONE);
        fl.findViewById(R.id.eventListView).setVisibility(View.VISIBLE);
    }

    private void showList(FrameLayout fl) {
        TimeTrialEventService timeTrialEventService;
        try {
            timeTrialEventService = TimeTrialEventServiceFactory.getInstance();
        } catch (Exception e) {
            Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
            timeTrialEventService = new TimeTrialEventServiceNull();
        }

        // Create the list adapter that takes the list and populates the list items
        UpcomingListAdapter upcomingListAdapter = new UpcomingListAdapter(
                this.getActivity(),
                timeTrialEventService.getUpcomingEvents());

        ListView upcomingListView = (ListView) fl.findViewById(R.id.eventListView);

        // Set the listView to use the adapter
        upcomingListView.setAdapter(upcomingListAdapter);

        // Create delegate that handles clicking on the list event
        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listItemselected = position;
                Intent intent = new Intent(getActivity(), UpcomingEvent.class);
                long eventId = view.getId();
                intent.putExtra("TT_ID", eventId);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void Notify() {
        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("events");
        if ( dataReady) {
            FrameLayout fl = (FrameLayout) this.getView();
            showListControl(fl);
            showList(fl);
        }
    }
}
