package com.uk.greer.sdwapp.activity.standing;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.uk.greer.sdwapp.OnDataReady;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.activity.completed.CompletedEvent;
import com.uk.greer.sdwapp.domain.TimeTrial;
import com.uk.greer.sdwapp.service.CacheCoordinator;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.util.List;

/**
 * Sets up the list view of events
 */
public class SeasonStandingListFragment extends Fragment implements OnDataReady {

    public static final String ARG_SECTION_NUMBER = "section_number";

    private static final String ARG_PARAM1 = "tabPosition";
    private static final String ARG_PARAM2 = "tabFunction";
    private static final String TT_ID = "tt_id";
    private int listItemselected;

    public static SeasonStandingListFragment newInstance(int tabPosition, String tabFunction) {

        Log.i("INFO", "Creating newInstance of: " + tabFunction);
        SeasonStandingListFragment fragment = new SeasonStandingListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, tabPosition);
        args.putString(ARG_PARAM2, tabFunction);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout fl = (RelativeLayout) inflater.inflate(
                R.layout.main_season_standing_list_fragment,
                container,
                false);

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical parent.

        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("standings");
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
        super.onSaveInstanceState(outState);

        ListView listView=null;
        try {
            listView = (ListView) getView().findViewById(R.id.standingListView);
            int position = listView.getFirstVisiblePosition();
            // Save the position o fthe first item so we know where to restore to
            outState.putInt("POSITION", position);
        }
        catch (Exception x) {
            Log.i("ERROR", "Unable to find view and save position");

        }

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

    private void showProgressControls(View fl) {
        fl.findViewById(R.id.waitingProgressBar).setVisibility(View.VISIBLE);
        fl.findViewById(R.id.waitingMessage).setVisibility(View.VISIBLE);
        fl.findViewById(R.id.eventListView).setVisibility(View.INVISIBLE);
    }

    private void showListControl(View fl) {
        fl.findViewById(R.id.waitingProgressBar).setVisibility(View.GONE);
        fl.findViewById(R.id.waitingMessage).setVisibility(View.GONE);
        fl.findViewById(R.id.standingListView).setVisibility(View.VISIBLE);
    }

    private void showList(View fl) {
        TimeTrialEventService timeTrialEventService;
        try {
            timeTrialEventService = TimeTrialEventServiceFactory.getInstance();
        } catch (Exception e) {
            Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
            timeTrialEventService = new TimeTrialEventServiceNull();
        }

        Bundle args = this.getArguments();
        int viewId = args.getInt(this.TT_ID);

        List<TimeTrial> events=timeTrialEventService.getCompletedEvents();

        // Create the list adapter that takes the list and populates the list items
        SeasonStandingListAdapter adapter = new SeasonStandingListAdapter(
            this.getActivity(),
            events);

        ListView standingListView = (ListView) fl.findViewById(R.id.standingListView);

        // Set the listView to use the adapter
        standingListView.setAdapter(adapter);

        // Create delegate that handles clicking on the list event
        standingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listItemselected = position;
                Intent intent = new Intent(getActivity(), CompletedEvent.class);
                intent.putExtra(TT_ID, view.getId());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void Notify() {
        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("standings");
        if ( dataReady) {
            FrameLayout fl = (FrameLayout) this.getView();
            showListControl(fl);
            showList(fl);
        }
    }
}
