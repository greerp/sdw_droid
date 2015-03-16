package com.uk.greer.sdwapp.service;

import com.uk.greer.sdwapp.domain.TimeTrial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greepau on 18/02/2015.
 */
public class TimeTrialEventServiceLocal implements TimeTrialEventService {

    private List<TimeTrial> list;


    public TimeTrialEventServiceLocal(){
        list = new ArrayList<TimeTrial>();
        int year = 2015-1900;
        list.add(createNew(1,1,"Sleeches Cross/Mayfield", new Date(year,2,15,9,0),"GS878", false));
        list.add(createNew(2,2,"Ladies Mile", new Date(year,2,22,8,30),"GS868", true));
        list.add(createNew(3,3,"Sleeches Cross/Wadhurst", new Date(year,3,16,18,30),"GS879", true));
        list.add(createNew(4,4,"Ladies Mile(Come & Try It)", new Date(year,3,23,18,45),"", true));
        list.add(createNew(5,5,"Sleeches Cross/Mayfield", new Date(year,3,30,19,0),"GS878", true));
        list.add(createNew(6,6, "Ashdown Forest", new Date(year,4,7,19,0),"GS898", true));
        list.add(createNew(7,7, "Sleeches Cross/Mayfield", new Date(year,4,14,19,0),"GS878", true));
        list.add(createNew(8,8, "Ladies Mile(Come & Try It)", new Date(year,4,21,19,0),"", true));
        list.add(createNew(9,9, "Hartfield/Whych Cross", new Date(year,4,28,19,0),"GS899", true));
        list.add(createNew(10,10, "East Peckham", new Date(year,5,2,19,30),"Q10/29", true));
        list.add(createNew(11,11, "Polhill", new Date(year,5,9,19,30),"Q10/18", true));
        list.add(createNew(12,12, "East Peckham", new Date(year,5,16,19,0),"Q10/29", true));
        list.add(createNew(13,13, "Winchet Hill", new Date(year,5,23,19,0),"", true));
        list.add(createNew(14,14, "East Peckham", new Date(year,5,30,19,0),"Q10/29", true));
    }


    @Override
    public List<TimeTrial> getUpcomingEvents(){
        return list;
    }

    @Override
    public TimeTrial getTimeTrial(long id){

        int listSize = list.size();
        for (int i=0;i<listSize;i++){
            TimeTrial tt = list.get(i);
            if (tt.getId()==id)
                return tt;
        }
        return null;
    }

    private TimeTrial createNew(int id, int eventNo, String name, Date eventDate, String course, boolean onLineEntry){
        TimeTrial tt = new TimeTrial();
        tt.setId(id);
        tt.setEventNo(eventNo);
        tt.setName(name);
        tt.setEventDate(eventDate);
        tt.setCourse(course);
        tt.setOnlineEntry(onLineEntry);
        return tt;
    }

}
