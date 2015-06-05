package com.uk.greer.sdwapp.activity.standing;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.OnDataReady;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.config.BundleProperty;
import com.uk.greer.sdwapp.domain.Standing;
import com.uk.greer.sdwapp.service.CacheCoordinator;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.util.Comparator;
import java.util.List;

/**
 * Sets up the list view of events
 */
public class SeasonStandingListFragment extends Fragment implements OnDataReady {

    public static final String ARG_SECTION_NUMBER = "section_number";

    private SortColumn sortColumn = new SortColumn();

    public static SeasonStandingListFragment newInstance(int tabPosition, String tabFunction, int season) {



        Log.i("INFO", "Creating newInstance of: " + tabFunction);
        SeasonStandingListFragment fragment = new SeasonStandingListFragment();
        Bundle args = new Bundle();
        args.putInt(BundleProperty.TAB_POSITION, tabPosition);
        args.putString(BundleProperty.TAB_FUNCTION, tabFunction);
        args.putInt(BundleProperty.SEASON_ID, season);
        fragment.setArguments(args);
        return fragment;
    }


    private void ClickListener(View v) {
        sortColumn.OnClick(v);

        ListView lv = (ListView) getView().findViewById(R.id.standingListView);
        ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
        adapter.sort(new Comparator() {
            @Override
            public int compare(Object lhs, Object rhs) {
                Standing lStd = (Standing)lhs;
                Standing rStd = (Standing)rhs;
                int result=0;

                switch (sortColumn.currentSortColumn.getId()){
                    case R.id.stdHeaderNameBtn:
                        String lname = lStd.getFirstName()+lStd.getLastName(); String rname=rStd.getFirstName()+rStd.getLastName();
                        result = lname.compareToIgnoreCase(rname);
                        break;
                    case R.id.stdHeaderHandicapPtsBtn:
                        result = Integer.compare(lStd.getHcppts(), rStd.getHcppts());
                        break;
                    case R.id.stdHeaderScratchPtsBtn:
                        result = Integer.compare(lStd.getScrpts(), rStd.getScrpts());
                        break;
                }
                return (!sortColumn.down? result*-1:result);
            }

        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout fl = (RelativeLayout) inflater.inflate(
                R.layout.main_season_standing_list_fragment,
                container,
                false);

        configureListeners(fl);

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical parent.
        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("standings");
        if (dataReady) {
            showListControl(fl);
            showList(fl);
        } else {
            showProgressControls(fl);
        }
        return fl;
    }

    private void configureListeners(View parentView) {

        Bundle args = this.getArguments();
        final int season;
        if (args!=null)
            season = args.getInt(BundleProperty.SEASON_ID);
        else {
            Log.e("EXCEPTION", "Unable to get determine season in - " + getClass().getName());
            season = 0;
        }

        parentView.findViewById(R.id.stdHeaderNameBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener(v);
            }
        });
        parentView.findViewById(R.id.stdHeaderScratchPtsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener(v);
            }
        });
        parentView.findViewById(R.id.stdHeaderHandicapPtsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener(v);
            }
        });

        parentView.findViewById(R.id.stdScratchCompBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SeasonStanding.class);
                intent.putExtra(BundleProperty.TT_COMPETITION, "SCRATCH");
                intent.putExtra(BundleProperty.TT_SEASON_ID, season);
                getActivity().startActivity(intent);
            }
        });

        parentView.findViewById(R.id.stdHandicapCompBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeasonStanding.class);
                intent.putExtra(BundleProperty.TT_COMPETITION, "HANDICAP");
                intent.putExtra(BundleProperty.TT_SEASON_ID, season);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ListView listView = null;
        try {
            listView = (ListView) getView().findViewById(R.id.standingListView);
            int position = listView.getFirstVisiblePosition();
            // Save the position o fthe first item so we know where to restore to
            outState.putInt(BundleProperty.POSITION, position);
        } catch (Exception x) {
            Log.i("ERROR", "Unable to find view and save position");

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt(BundleProperty.POSITION);
            ListView listView = (ListView) this.getView().findViewById(R.id.eventListView);
            if (position > 0) {
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
        //TODO: replace best counts with variable
        List<Standing> standings = timeTrialEventService.getStandings(3,10,10);

        // Create the list adapter that takes the list and populates the list items
        SeasonStandingListAdapter adapter =
                new SeasonStandingListAdapter(this.getActivity(), standings);

        ListView standingListView = (ListView) fl.findViewById(R.id.standingListView);

        // Set the listView to use the adapter
        standingListView.setAdapter(adapter);

        // Create delegate that handles clicking on the list event
        standingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO: Implement Individuals Season Results activity
                Intent intent = new Intent(getActivity(), SeasonStanding.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void Notify() {
        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("standings");
        if (dataReady) {
            FrameLayout fl = (FrameLayout)getView();
            if ( fl==null){
                AppManager.ShowMessageBox("Null layout.. cannot continue!");
            }
            else {
                showListControl(fl);
                showList(fl);
            }
        }
    }

    private class SortColumn {
        Button currentSortColumn;
        Boolean down;

        final int DN_IMAGE = R.drawable.arrow_down_float;
        final int UP_IMAGE = R.drawable.arrow_up_float;

        public String OnClick(View v) {
            Button b = (Button) v;
            if (currentSortColumn != null) {
                currentSortColumn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                if (v == currentSortColumn) {
                    if (down) {
                        down = false;
                        b.setCompoundDrawablesWithIntrinsicBounds(0, 0, UP_IMAGE, 0);
                        return (String) b.getTag();
                    }
                }
            }
            down = true;
            currentSortColumn = b;
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, DN_IMAGE, 0);
            return (String) b.getTag();
        }
    }
}
