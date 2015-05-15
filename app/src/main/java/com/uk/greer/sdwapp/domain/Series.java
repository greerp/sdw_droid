package com.uk.greer.sdwapp.domain;

import java.util.Date;

/**
 * Created by greepau on 14/05/2015.
 */
public class Series {

    private int id;
    private String name;
    private Date start;
    private Date end;


    public static Series newInstance(int id, String name, Date start, Date end){

        Series series = new Series();
        series.end = end;
        series.start = start;
        series.id =id;
        series.name = name;
        return series;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }




}
