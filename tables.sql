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

create index coursesidx1 on courses(id);
create index eventsidx1 on events(id);
create index usersidx1 on users(id);	
create index entriesidx1 on entries(id);	


CREATE VIEW v_results as select t.id, t.eventid, t.userid, t.scrpts, t.hcppts, t.status, t.time, 
			e.name as eventname, e.seriesid, e.eventdate, e.countsforpb, e.eventoutcome 
			from entries t join events e on t.eventid=e.id;


CREATE VIEW v_timetrials as select events.id, events.eventdate, 
				courses.name as coursename, courses.coursecode, courses.distance, 
				events.name as eventname, coursenotes, events.seriesid  from events join courses 
				on events.courseid=courses.id order by events.eventdate;


create view v_ttentries as select entries.id, entries.eventid, 
				entries.userid, users.username, users.firstname,
				users.lastname, entries.signondate, entries.signonmethod, entries.handicap 
				from entries join users on entries.userid=users.id;

/*				
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
*/

/* The following qery replaces v_ttstandings but requires parameters hence it is no longer a view 

/* The following can be used if limit is not passed in a a parameter */
/* ifnull((select value from appvalues where name="TTSTANDINGTOP"),"10") */
/*
select entries.userid, users.username, users.firstname,users.lastname, events.seriesid,
	count(*) as entered,
	sum(scrpts) as totscrpts,
	(select sum(e1.hcppts) from entries e1 where e1.userid=users.id and e1.id in (select e2.id from entries e2, events v1 where e2.eventid=v1.id and v1.seriesid=events.seriesid and e2.userid=e1.userid and hcppts<>'' order by hcppts desc limit ?)) as besthcppts,
	sum(hcppts) as hcppts,
	(select sum(e1.scrpts) from entries e1 where e1.userid=users.id and e1.id in (select e2.id from entries e2, events v1 where e2.eventid=v1.id and v1.seriesid=events.seriesid and e2.userid=e1.userid and scrpts<>'' order by scrpts desc limit ?)) as bestscrpts,
	(select count(*) from entries e2 where e2.userid=entries.userid and status='DNS') as dns,
	(select count(*) from entries e2 where e2.userid=entries.userid and status='FIN') as fin,
	(select count(*) from entries e2 where e2.userid=entries.userid and status='DNF') as dnf
from entries 
join users on entries.userid=users.id 
join events on entries.eventid=events.id 
where entries.status in ('FIN','DNS','DNF') and events.seriesid=?
group by entries.userid, users.username, users.firstname,users.lastname,events.seriesid;
*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		