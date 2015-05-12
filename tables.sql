create table courses(
	id INT, 
	name TEXT, 
	coursecode TEXT,
	coursemapurl TEXT,	coursenotes TEXT,	distance DOUBLE);

create table events(
	id INT, 
	name TEXT,            
	eventdate DATETIME, 
	courseid int,
	eventurl TEXT,        
	maxentries INT,       
	eventoutcome TEXT,    
	notes TEXT, 
	countsforpb BOOLEAN );  


create table users(
	id INT,
	username TEXT,
	firstname TEXT,
	lastname TEXT);

create table entries(
	id INT, 
	eventid INT, 
	userid INT,
	signondate DATETIME, 
	signonmethod TEXT, 
	handicap DOUBLE, 
	startno INT,
	time INT,
	status TEXT);


CREATE VIEW v_timetrials as select events.id, events.eventdate, 
				courses.name as coursename, courses.coursecode, courses.distance, 
				events.name as eventname, coursenotes from events join courses 
				on events.courseid=courses.id order by events.eventdate;


create view v_ttentries as select entries.id, entries.eventid, 
				entries.userid, users.username, users.firstname,
				users.lastname, entries.signondate, entries.signonmethod, entries.handicap 
				from entries join users on entries.userid=users.id;
