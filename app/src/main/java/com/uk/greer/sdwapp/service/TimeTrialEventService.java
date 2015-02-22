package com.uk.greer.sdwapp.service;

import com.uk.greer.sdwapp.domain.TimeTrial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greepau on 18/02/2015.
 */
public class TimeTrialEventService {

    private List<TimeTrial> list;
    private static TimeTrialEventService ttService;

    public static TimeTrialEventService getInstance(){
        if (ttService==null){
            ttService=new TimeTrialEventService();
        }
        return ttService;
    }

    private TimeTrialEventService(){
        list = new ArrayList<TimeTrial>();
        list.add(createNew(1,"Sleeches Cross/Mayfield", new Date(2015,3,15,9,0),"GS878", false));
        list.add(createNew(2,"Ladies Mile", new Date(2015,3,22,8,30),"GS868", true));
        list.add(createNew(3,"Sleeches Cross/Wadhurst", new Date(2015,4,16,18,30),"GS879", true));
        list.add(createNew(4,"Ladies Mile(Come & Try It)", new Date(2015,4,23,18,45),"", true));
        list.add(createNew(5,"Sleeches Cross/Mayfield", new Date(2015,4,30,19,0),"GS878", true));
        list.add(createNew(6, "Ashdown Forest", new Date(2015,5,7,19,0),"GS898", true));
        list.add(createNew(7, "Sleeches Cross/Mayfield", new Date(2015,5,14,19,0),"GS878", true));
        list.add(createNew(8, "Ladies Mile(Come & Try It)", new Date(2015,5,21,19,0),"", true));
        list.add(createNew(9, "Hartfield/Whych Cross", new Date(2015,5,28,19,0),"GS899", true));
        list.add(createNew(10, "East Peckham", new Date(2015,6,2,19,30),"Q10/29", true));
        list.add(createNew(11, "Polhill", new Date(2015,6,9,19,30),"Q10/18", true));
        list.add(createNew(12, "East Peckham", new Date(2015,6,16,19,0),"Q10/29", true));
        list.add(createNew(13, "Winchet Hill", new Date(2015,6,23,19,0),"", true));
        list.add(createNew(14, "East Peckham", new Date(2015,6,30,19,0),"Q10/29", true));
    }


    public List<TimeTrial> getUpcomingEvents(){
        return list;
    }

    public TimeTrial getTimeTrial(long id){

        int listSize = list.size();
        for (int i=0;i<listSize;i++){
            TimeTrial tt = list.get(i);
            if (tt.getId()==id)
                return tt;
        }
        return null;
    }

    private TimeTrial createNew(int id, String name, Date eventDate, String course, boolean onLineEntry){
        TimeTrial tt = new TimeTrial();
        tt.setId(id);
        tt.setName(name);
        tt.setEventDate(eventDate);
        tt.setCourse(course);
        tt.setOnlineEntry(onLineEntry);
        return tt;
    }

}
