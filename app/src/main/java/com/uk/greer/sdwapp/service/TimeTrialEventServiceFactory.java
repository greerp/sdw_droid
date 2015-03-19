package com.uk.greer.sdwapp.service;

import android.content.Context;
import android.util.Log;

import com.uk.greer.sdwapp.MainActivity2;
import com.uk.greer.sdwapp.config.ApplicationProperty;

/**
 * Created by greepau on 14/03/2015.
 */
public class TimeTrialEventServiceFactory {

    private static TimeTrialEventService ttService;

    public static TimeTrialEventService getInstance() throws Exception {

        if (ttService == null) {

            Context context = MainActivity2.getContext().getApplicationContext();

            String serviceToUse = ApplicationProperty.get("ttservice");
            Log.d("CONFIG", "Service to use: " + serviceToUse);
            switch (serviceToUse) {
                case "cache":
                    ttService = new TimeTrialEventServiceCache((context));
                    break;
                case "local":
                    ttService = new TimeTrialEventServiceLocal();
                    break;
                case "gae":
                    ttService = new TimeTrialEventServiceGae();
                    break;
                default:
                    throw new Exception("Undefined TT service");
            }
        }
        return ttService;
    }
}
