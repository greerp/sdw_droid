package com.uk.greer.sdwapp.service;

import com.uk.greer.sdwapp.domain.Participant;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by greepau on 14/03/2015.
 */
public class TimeTrialEventServiceGae implements TimeTrialEventService {

    private final TimeTrialEventService service;

    public TimeTrialEventServiceGae(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://10.0.2.2:8888")
                .setLogLevel(RestAdapter.LogLevel.FULL).build();


        service = restAdapter.create(TimeTrialEventService.class);
    }


    @Override
    public List<TimeTrial> getCompletedEvents() {
        return null;
    }

    @Override
    public List<TimeTrial> getUpcomingEvents() {

        return service.getUpcomingEvents();
    }


    @Override
    public TimeTrial getTimeTrial(long id) {
        return service.getTimeTrial(id);
    }

    @Override
    public List<Participant> getEntries(long ttId) {
        return null;
    }

}
