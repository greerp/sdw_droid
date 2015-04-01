package com.uk.greer.sdwapp.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by greepau on 18/02/2015.
 */
public class TimeTrial {

    private String name;
    private Date eventDate;
    private String course;
    private String eventType;
    private boolean onlineEntry;
    private String eventUrl;
    private int maxEntries;
    private List<Participant> participants;
    private Double distance;
    private long id;
    private int eventNo;

    public static TimeTrial newInstance(int id, int eventNo, String name, Date eventDate, String course, boolean onLineEntry){
        TimeTrial tt = new TimeTrial();
        tt.setId(id);
        tt.setEventNo(eventNo);
        tt.setName(name);
        tt.setEventDate(eventDate);
        tt.setCourse(course);
        tt.setOnlineEntry(onLineEntry);
        return tt;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isOnlineEntry() {
        return onlineEntry;
    }

    public void setOnlineEntry(boolean onlineEntry) {
        this.onlineEntry = onlineEntry;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public int getMaxEntries() {
        return maxEntries;
    }

    public void setMaxEntries(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public int getEventNo() {
        return eventNo;
    }
}
