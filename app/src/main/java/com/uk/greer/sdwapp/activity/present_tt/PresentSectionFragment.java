package com.uk.greer.sdwapp.activity.present_tt;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.service.TimeTrialEventService;

/**
 * Created by greepau on 19/02/2015.
 */
public class PresentSectionFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListView rootView = (ListView)inflater.inflate(
                R.layout.activity_future_tt,
                container,
                false);

        TimeTrialEventService timeTrialEventService
                = new TimeTrialEventService();

        PresentListAdapter presentListAdapter = new PresentListAdapter(
                this.getActivity(),
                timeTrialEventService.getFutureEvents());

        rootView.setAdapter(presentListAdapter);
        return rootView;
    }
}
