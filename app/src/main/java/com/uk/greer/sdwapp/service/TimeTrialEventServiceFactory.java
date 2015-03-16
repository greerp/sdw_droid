package com.uk.greer.sdwapp.service;

/**
 * Created by greepau on 14/03/2015.
 */
public class TimeTrialEventServiceFactory {

    private static TimeTrialEventService ttService;

    public static TimeTrialEventService getInstance(){

        //TODO: Determine which implementation we want to use

        if (ttService==null){
            //ttService=new TimeTrialEventServiceLocal();
            ttService=new TimeTrialEventServiceGae();
        }
        return ttService;
    }
}
