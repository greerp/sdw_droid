package com.uk.greer.sdwapp.activity.completed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.config.BundleProperty;
import com.uk.greer.sdwapp.service.CacheCoordinator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletedEventScratchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedEventScratchResultFragment extends Fragment {


    private int eventId;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CompletedEventScratchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletedEventScratchResultFragment newInstance(int eventId) {
        CompletedEventScratchResultFragment fragment = new CompletedEventScratchResultFragment();
        Bundle args = new Bundle();
        args.putInt(BundleProperty.TT_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    public CompletedEventScratchResultFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        RelativeLayout fl = (RelativeLayout) inflater.inflate(
                R.layout.completed_event_scratch_result_fragment,
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


}
