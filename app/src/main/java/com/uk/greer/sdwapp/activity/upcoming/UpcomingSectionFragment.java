package com.uk.greer.sdwapp.activity.upcoming;

import android.content.Context;
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
import com.uk.greer.sdwapp.service.TimeTrialEventServiceLocal;
import com.uk.greer.sdwapp.service.TimeTrialEventServiceNull;

/**
 * Created by greepau on 19/02/2015.
 */
public class UpcomingSectionFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListView upcomingListView = (ListView)inflater.inflate(
                R.layout.fragment_upcoming_section,
                container,
                false);

        TimeTrialEventService timeTrialEventService;
        try {
            timeTrialEventService = TimeTrialEventServiceFactory.getInstance();
        }
        catch (Exception e){
            Log.e("EXCEPTION", "Unable to create Time Trial Service, using default");
            timeTrialEventService = new TimeTrialEventServiceNull();
        }

        UpcomingListAdapter upcomingListAdapter = new UpcomingListAdapter(
                this.getActivity(),
                timeTrialEventService.getUpcomingEvents());

        upcomingListView.setAdapter(upcomingListAdapter);

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
