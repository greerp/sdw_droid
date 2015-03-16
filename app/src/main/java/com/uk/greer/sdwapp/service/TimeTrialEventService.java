package com.uk.greer.sdwapp.service;

import com.uk.greer.sdwapp.domain.TimeTrial;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by greepau on 14/03/2015.
 */
public interface TimeTrialEventService {


    @GET("/upcomingtt")
    List<TimeTrial> getUpcomingEvents();

    @GET("/gettt/{id}")
    TimeTrial getTimeTrial(long id);



}
