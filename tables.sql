create table series(
	id int,
	name TEXT,
	seriesstart DATETIME,
	seriesend DATETIME
);


create table courses(
	id INT, 
	name TEXT, 
	coursecode TEXT,
	coursemapurl TEXT,	coursenotes TEXT,	distance DOUBLE);

create table events(
	id INT, 
	seriesid INT,
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
	status TEXT,
    scrpts INT,
    hcppts INT);


CREATE VIEW v_timetrials as select events.id, events.eventdate, 
				courses.name as coursename, courses.coursecode, courses.distance, 
				events.name as eventname, coursenotes from events join courses 
				on events.courseid=courses.id order by events.eventdate;


create view v_ttentries as select entries.id, entries.eventid, 
				entries.userid, users.username, users.firstname,
				users.lastname, entries.signondate, entries.signonmethod, entries.handicap 
				from entries join users on entries.userid=users.id;

CREATE VIEW v_ttstandings as
        select entries.userid, users.username, users.firstname,users.lastname, events.seriesid,
			count(*) as entered,
			sum(scrpts) as scrpts,
			sum(hcppts) as hcppts,
			(select count(*) from entries e2 where e2.userid=entries.userid and status="DNS") as dns ,
			(select count(*) from entries e2 where e2.userid=entries.userid and status="FIN") as fin,
			(select count(*) from entries e2 where e2.userid=entries.userid and status="DNF") as dnf
		from entries 
		join users on entries.userid=users.id 
		join events on entries.eventid=events.id 
		where entries.status in ('FIN','DNS','DNF') 
		group by entries.userid, users.username, users.firstname,users.lastname,events.seriesid;






				