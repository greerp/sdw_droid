package com.uk.greer.sdwapp.activity.completed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.config.BundleProperty;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventSummaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    public EventSummaryFragment() {
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
        return inflater.inflate(R.layout.completed_event_summary_fragment, container, false);
    }



}