package com.uk.greer.sdwapp.persist;

/**
 * Created by greepau on 17/03/2015.
 */
public class TableDefinition {

    // These tablke definitions should replicate those on the GAE Server

    public static String CONFIG ="config";

    public static String COURSE ="courses";
    public static String COURSE_ID="id";
    public static String COURSE_NAME="name";
    public static String COURSE_CODE="coursecode";
    public static String COURSE_MAPURL="coursemapurl";
    public static String COURSE_NOTES="coursenotes";
    public static String COURSE_DISTANCE="distance";


    public static String CONFIG_TABLE = "create table config(name TEXT, value TEXT)";


    public static String COURSES_TABLE="create table courses(" +
            "id INT, " +
            "name TEXT, " +
            "coursecode TEXT," +
            "coursemapurl TEXT," +
            "coursenotes TEXT," +
            "distance DOUBLE)";


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


    public static String USERS_TABLE="create table users(" +
            "id INT," +
            "username TEXT," +
            "firstname TEXT," +
            "lastname TEXT)";

    // Time is stored in seconds
    // Status - Complete, DNF, DNS
    public static String ENTRIES_TABLE ="create table entries(" +
            "id INT, " +
            "eventid INT, " +
            "userid INT," +
            "signondate DATETIME, " +
            "signonmethod TEXT, " +
            "handicap DOUBLE, " +
            "startno INT," +
            "time INT," +
            "status TEXT)";


    public static String TIMETRIALS_VIEW="CREATE VIEW v_timetrials as select events.id, events.eventdate, " +
            "courses.name as coursename, courses.coursecode, courses.distance, " +
            "events.name as eventname, coursenotes from events join courses " +
            "on events.courseid=courses.id order by events.eventdate";


    public static String ENTRIES_VIEW="create view v_ttentries as select participants.id, participants.eventid, " +
            "participants.userid, users.username, users.firstname," +
            "users.lastname, participants.signondate, participants.signonmethod, participants.handicap " +
            "from participants join users on participants.userid=users.id;";

}
