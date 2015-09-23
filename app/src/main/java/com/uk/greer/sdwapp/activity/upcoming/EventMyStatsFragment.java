package com.uk.greer.sdwapp.activity.upcoming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uk.greer.sdwapp.R;

/**
 * Created by greepau on 13/04/2015.
 */
public class EventMyStatsFragment extends Fragment {

    public static EventMyStatsFragment newInstance(int ttId){

        EventMyStatsFragment fragment = new EventMyStatsFragment();
        Bundle args = new Bundle();
        //TODO: Add parameters here
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.upcoming_event_my_stats_fragment, container, false);
        return v;
    }
}
