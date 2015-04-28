package com.uk.greer.sdwapp.service;

import com.uk.greer.sdwapp.domain.Participant;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greepau on 18/03/2015.
 */
public class TimeTrialEventServiceNull implements TimeTrialEventService {
    @Override
    public List<TimeTrial> getCompletedEvents() {
        return null;
    }

    @Override
    public List<TimeTrial> getUpcomingEvents() {

        TimeTrial tt = getTimeTrial();
        ArrayList<TimeTrial> ttArray = new ArrayList<TimeTrial>();
        ttArray.add(tt);
        return ttArray;
    }

    private TimeTrial getTimeTrial() {
        TimeTrial tt = new TimeTrial();
        tt.setOnlineEntry(false);
        tt.setCourse("");
        tt.setDistance(0.0);
        tt.setEventDate(new Date(0));
        tt.setEventNo(0);
        tt.setEventType("");
        tt.setEventUrl("");
        tt.setId(0);
        tt.setMaxEntries(0);
        tt.setName("");
        return tt;
    }

    @Override
    public TimeTrial getTimeTrial(long id) {
        return getTimeTrial();
    }

    @Override
    public List<Participant> getEntries(long ttId) {
        return null;
    }

    @Override
    public int getEntryCount(long ttId) {
        return 0;
    }
}
