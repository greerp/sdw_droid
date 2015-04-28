package com.uk.greer.sdwapp.service;

import com.uk.greer.sdwapp.domain.Participant;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by greepau on 14/03/2015.
 */
public interface TimeTrialEventService {

    @GET("/completedtt")
    List<TimeTrial> getCompletedEvents();

    @GET("/upcomingtt")
    List<TimeTrial> getUpcomingEvents();

    @GET("/gettt/{id}")
    TimeTrial getTimeTrial(long id);

    @GET("/getentrees/{id}")
    List<Participant> getEntries(long ttId);

    int getEntryCount(long ttId);

}
