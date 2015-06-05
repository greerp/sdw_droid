package com.uk.greer.sdwapp.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by greepau on 18/02/2015.
 */
public class TimeTrial implements DomainObject{

    private String name;
    private Series series;
    private Date eventDate;
    private String course;
    private String eventType;
    private boolean onlineEntry;
    private String eventUrl;
    private int maxEntries;
    private List<Entry> entries;
    private Double distance;
    private int id;
    private int eventNo;
    private String notes;
    private int seriesId;



    public static TimeTrial newInstance(int id, int eventNo, String name, Date eventDate, String course, boolean onLineEntry, String notes, int seriesId){
        TimeTrial tt = new TimeTrial();
        tt.setId(id);
        tt.setEventNo(eventNo);
        tt.setName(name);
        tt.setEventDate(eventDate);
        tt.setCourse(course);
        tt.setOnlineEntry(onLineEntry);
        tt.setNotes(notes);
        tt.setSeriesId(seriesId);
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventNo(int eventNo) {
        this.eventNo = eventNo;
    }

    public int getEventNo() {
        return eventNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }



}
