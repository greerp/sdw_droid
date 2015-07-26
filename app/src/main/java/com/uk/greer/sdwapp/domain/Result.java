package com.uk.greer.sdwapp.domain;

/**
 * Created by greepau on 02/06/2015.
 */
public class Result implements DomainObject{

    private int id;
    private int eventId;
    private int userId;
    private int scrpts;
    private int hcppts;
    private String status;
    private int time;
    private int handicap;
    private String firstName;
    private String lastName;
    private int scrpos;
    private int hcppos;


    //public static Result newInstance(int id, int eventId, int userId, String firstName, String lastName, int scrpts, int hcppts, String status, int time, int scratchpos, int handicappos ){
    public static Result newInstance(int id, int eventId, int userId, String firstName, String lastName,
                                     int scrpts, int hcppts, String status, int time, int handicap, int scrpos, int hcppos){
        Result result = new Result();
        result.id = id;
        result.eventId = eventId;
        result.userId = userId;
        result.scrpts = scrpts;
        result.hcppts = hcppts;
        result.status = status;
        result.time = time;
        result.firstName = firstName;
        result.lastName = lastName;
        result.handicap = handicap;
        result.scrpos = scrpos;
        result.hcppos = hcppos;
        return result;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScrpts() {
        return scrpts;
    }

    public void setScrpts(int scrpts) {
        this.scrpts = scrpts;
    }

    public int getHcppts() {
        return hcppts;
    }

    public void setHcppts(int hcppts) {
        this.hcppts = hcppts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getHandicap() {
        return handicap;
    }

    public void setHandicap(int handicap) {
        this.handicap = handicap;
    }

    public int getScrpos() {
        return scrpos;
    }

    public void setScrpos(int scrpos) {
        this.scrpos = scrpos;
    }

    public int getHcppos() {
        return hcppos;
    }

    public void setHcppos(int hcppos) {
        this.hcppos = hcppos;
    }
}
