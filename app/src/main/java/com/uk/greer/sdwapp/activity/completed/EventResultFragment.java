package com.uk.greer.sdwapp.activity.completed;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.uk.greer.sdwapp.AppManager;
import com.uk.greer.sdwapp.service.OnDataReady;
import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.config.BundleProperty;
import com.uk.greer.sdwapp.domain.Result;
import com.uk.greer.sdwapp.service.CacheCoordinator;
import com.uk.greer.sdwapp.service.TimeTrialEventService;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceFactory;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventResultFragment extends Fragment implements OnDataReady {


    private BundleProperty.COMPETITION compType;
    private int eventId;
    private SortColumn sortColumn = new SortColumn();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CompletedEventScratchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventResultFragment newInstance(BundleProperty.COMPETITION compType, int eventId) {
        EventResultFragment fragment = new EventResultFragment();
        Bundle args = new Bundle();
        args.putInt(BundleProperty.TT_EVENT_ID, eventId);
        args.putSerializable(BundleProperty.TT_COMPETITION, compType);
        fragment.setArguments(args);
        return fragment;
    }

    public EventResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getInt(BundleProperty.TT_EVENT_ID);
            compType = (BundleProperty.COMPETITION) getArguments().getSerializable(BundleProperty.TT_COMPETITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout fl = (RelativeLayout) inflater.inflate(
                R.layout.completed_event_result_fragment,
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
        fl.findViewById(R.id.resultListView).setVisibility(View.INVISIBLE);
    }

    private void showListControl(View fl) {
        fl.findViewById(R.id.waitingProgressBar).setVisibility(View.GONE);
        fl.findViewById(R.id.waitingMessage).setVisibility(View.GONE);
        fl.findViewById(R.id.resultListView).setVisibility(View.VISIBLE);
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
        List<Result> results = timeTrialEventService.getEventFinishers(eventId);

        Collections.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result lRes, Result rRes) {
                return Integer.compare(
                        compType== BundleProperty.COMPETITION.SCRATCH ? lRes.getScrpos(): lRes.getHcppos() ,
                        compType== BundleProperty.COMPETITION.SCRATCH ? rRes.getScrpos(): rRes.getHcppos());
            }
        });



        // Create the list adapter that takes the list and populates the list items
        EventResultAdapter adapter =
                new EventResultAdapter(this.getActivity(), results, compType);

        ListView listView = (ListView) fl.findViewById(R.id.resultListView);

        // Set the listView to use the adapter
        listView.setAdapter(adapter);

//        // Create delegate that handles clicking on the list event
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                //TODO: Implement Individuals Season Results activity
//                Intent intent = new Intent(getActivity(), SeasonStanding.class);
//                getActivity().startActivity(intent);
//            }
//        });
    }

    private void configureListeners(RelativeLayout parentView) {
        //Bundle args = this.getArguments();

        parentView.findViewById(R.id.resHeaderPositionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener(v);
            }
        });
        parentView.findViewById(R.id.resHeaderNameBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener(v);
            }
        });
        parentView.findViewById(R.id.resHeaderTimeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener(v);
            }
        });
        parentView.findViewById(R.id.resHeaderPointsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener(v);
            }
        });
    }

    private void ClickListener(View v) {
        sortColumn.OnClick(v);

        ListView lv = (ListView) getView().findViewById(R.id.resultListView);
        ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
        adapter.sort(new Comparator() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Object lhs, Object rhs) {
                Result lRes = (Result) lhs;
                Result rRes = (Result) rhs;
                int result = 0;

                switch (sortColumn.currentSortColumn.getId()) {
                    case R.id.resHeaderPositionBtn:
                        result = Integer.compare(
                                compType== BundleProperty.COMPETITION.SCRATCH ? lRes.getScrpts(): lRes.getHcppts() ,
                                compType== BundleProperty.COMPETITION.SCRATCH ? rRes.getScrpts(): rRes.getHcppts());
                        break;
                    case R.id.resHeaderNameBtn:
                        String name1 = rRes.getFirstName() + rRes.getLastName();
                        String name2 = lRes.getFirstName() + lRes.getLastName();
                        result = name1.compareToIgnoreCase(name2);
                        break;
                    case R.id.resHeaderTimeBtn:
                        result = Integer.compare(
                                compType== BundleProperty.COMPETITION.SCRATCH ? lRes.getTime(): lRes.getTime()-lRes.getHandicap() ,
                                compType== BundleProperty.COMPETITION.SCRATCH ? rRes.getTime(): rRes.getTime()-rRes.getHandicap());
                        break;
                    case R.id.resHeaderPointsBtn:
                        result = Integer.compare(
                                compType== BundleProperty.COMPETITION.SCRATCH ? lRes.getScrpts(): lRes.getHcppts() ,
                                compType== BundleProperty.COMPETITION.SCRATCH ? rRes.getScrpts(): rRes.getHcppts());
                        break;
                }
                return (!sortColumn.down ? result * -1 : result);
            }

        });
        adapter.notifyDataSetChanged();
    }


    @Override
    public void Notify() {
        boolean dataReady = CacheCoordinator.getInstance().getCacheStatus("standings");
        if (dataReady) {
            FrameLayout fl = (FrameLayout)getView();
            if ( fl==null){
                AppManager.ShowMessageBox(getActivity(), "Null layout.. cannot continue!");
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
