package com.uk.greer.sdwapp.persist;

/**
 * Created by greepau on 17/03/2015.
 */
public class TableDefinition {

    // These tablke definitions should replicate those on the GAE Server

    public static String COURSES_TABLE="create table courses(" +
            "id INT, " +
            "name TEXT, " +
            "coursecode TEXT," +
            "coursemapurl TEXT," +
            "coursenotes TEXT," +
            "distance DOUBLE)";


    public static String COURSE_TABLE="courses";

    public static String COURSE_ID="id";
    public static String COURSE_NAME="name";
    public static String COURSE_CODE="coursecode";
    public static String COURSE_MAPURL="coursemapurl";
    public static String COURSE_NOTES="coursenotes";
    public static String COURSE_DISTANCE="distance";

    public static String EVENTS_TABLE="create table events(" +
            "id INT, " +
            "name TEXT, " +           // Default text
            "eventdate DATETIME, " +
            "courseid int," +
            "eventurl TEXT, " +       // Event Web Site URL
            "maxentries INT, " +      // Max number of Participants
            "eventoutcome TEXT, " +   // Canceled, Complete, Shortened, Abandoned
            "notes TEXT, " +
            "countsforpb BOOLEAN )";  // Flag to indicate whether the event had circumstanaces that would prevent it being counted in PB's for the course


    public static String PARTICIPANTS_TABLE="create table participants(" +
            "id INT, " +
            "eventid INT, " +
            "signondate DATETIME, " +
            "signonmethod TEXT, " +
            "handicap DOUBLE )";

    // Time is stored in seconds
    // Status - Complete, DNF, DNS
    public static String EVENTRESULTS_TABLE="create table results(" +
            "id INT, " +
            "eventid INT, " +
            "participantid INT, " +
            "time INT, " +
            "status TEXT )";




}
