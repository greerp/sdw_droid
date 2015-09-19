package com.uk.greer.sdwapp.activity.upcoming;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.OnDataReady;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.Entry;
import com.uk.greer.sdwapp.service.CacheCoordinator;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.util.List;

/**
 * Sets up the list view of events
 */
public class EntriesListFragment extends Fragment implements OnDataReady {

    public static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TT_ID = "tt_id";

    private int listItemselected;

    public static EntriesListFragment newInstance(int ttId) {

        Log.i("INFO", "Creating newInstance of: " + EntriesListFragment.class);
        EntriesListFragment fragment = new EntriesListFragment();
        Bundle args = new Bundle();
        args.putInt(TT_ID, ttId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout fl = (RelativeLayout) inflater.inflate(
                R.layout.upcoming_entries_list_fragment,
                container,
                false);

        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("events");
        if (dataReady) {
            showListControl(fl);
            showList(fl);
        } else {
            showProgressControls(fl);
        }

        return fl;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ListView listView = null;
        try {
            listView = (ListView) getView().findViewById(R.id.entriesListView);
            int position = listView.getFirstVisiblePosition();
            // Save the position o fthe first item so we know where to restore to
            outState.putInt("POSITION", position);
        } catch (Exception x) {
            Log.i("ERROR", "Unable to find view and save position");

        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt("POSITION");
            ListView listView = (ListView) this.getView().findViewById(R.id.entriesListView);
            if (position > 0) {
                //listView.scrollListBy();
                listView.setSelection(position);
            }
        }
    }

    private void showProgressControls(RelativeLayout fl) {
        fl.findViewById(R.id.waitingProgressBar).setVisibility(View.VISIBLE);
        fl.findViewById(R.id.waitingMessage).setVisibility(View.VISIBLE);
        fl.findViewById(R.id.entriesListView).setVisibility(View.INVISIBLE);
    }

    private void showListControl(RelativeLayout fl) {
        fl.findViewById(R.id.waitingProgressBar).setVisibility(View.GONE);
        fl.findViewById(R.id.waitingMessage).setVisibility(View.GONE);
        fl.findViewById(R.id.entriesListView).setVisibility(View.VISIBLE);
    }

    private void showList(RelativeLayout fl) {
        TimeTrialEventService timeTrialEventService;
        Bundle args = this.getArguments();
        if ( args!=null) {
            int ttId = args.getInt(this.TT_ID);

            try {
                timeTrialEventService = TimeTrialEventServiceFactory.getInstance();
            } catch (Exception e) {
                Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
                timeTrialEventService = new TimeTrialEventServiceNull();
            }

            //List<TimeTrial> events = timeTrialEventService.getCompletedEvents();
            List<Entry> entries = timeTrialEventService.getEntries(ttId);

            // Create the list adapter that takes the list and populates the list items
            EntriesListAdapter entriesListAdapter = new EntriesListAdapter(
                    this.getActivity(),
                    entries);

            ListView listView = (ListView) fl.findViewById(R.id.entriesListView);

            // Set the listView to use the adapter
            listView.setAdapter(entriesListAdapter);

        }
        else {
            Log.e("ERROR", "No arguments, instance may have been created directly an dnot through newInstance");
        }
    }

    @Override
    public void Notify() {
        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("events");
        if (dataReady) {
            RelativeLayout fl = (RelativeLayout) this.getView();
            if (fl == null) {
                AppManager.ShowMessageBox(getActivity(), "Null layout.. cannot continue!");
            } else {
                showListControl(fl);
                showList(fl);
            }

        }
    }
}
