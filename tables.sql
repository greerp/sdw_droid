/*
	To update the database:
	
	1. run updatedb-local.cmd, tis will create the database locally and copy it to the assets source folder
	2. run delremotedb.cmd, this will remove the existign database from the emulator.... the emultaor must be running 
	
	When running the application gradle will detect the database is missing from the emulator and copy it from the assets folder

*/


create table series(
	id int,
	name TEXT,
	seriesstart DATETIME,
	seriesend DATETIME
);

create table course(
	id INT, 
	name TEXT, 
	coursecode TEXT,
	coursemapurl TEXT,
	coursenotes TEXT,
	distance DOUBLE);

create table event(
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

create table user(
	id INT,
	username TEXT,
	firstname TEXT,
	lastname TEXT,
	capacity TEXT);

create table entry(
	id INT, 
	eventid INT, 
	userid INT,
	signondate DATETIME, 
	signonmethod TEXT, 
	handicap INT, 
	startno INT,
	time INT,
	status TEXT,
    scrpts INT,
    hcppts INT,
	scrpos INT,
	hcppos INT
	);

create table official(
	id INT, 
	eventid INT, 
	userid INT, 
	capacity TEXT
	);


/* 
	This strcuture will accomodate multiple PB's however there is a constraint for an individual 
	the times must improve with later dates 
*/

create table personalbest(
	id INT,
	userid INT,
	courseid INT,
	eventid INT, 
	time INT,
	eventdate DATETIME
);



create index coursesidx1 on course(id);
create index eventsidx1 on event(id);
create index usersidx1 on user(id);	
create index entriesidx1 on entry(id);	


CREATE VIEW v_result as select t.id, t.eventid, t.userid, t.scrpts, t.hcppts, t.status, t.time, 
			e.name as eventname, e.seriesid, e.eventdate, e.countsforpb, e.eventoutcome, u.firstname, 
			u.lastname, t.handicap, t.scrpos, t.hcppos
			from entry t join event e on t.eventid=e.id join user u on t.userid=u.id;


CREATE VIEW v_timetrial as select event.id, event.eventdate, 
				course.name as coursename, course.coursecode, course.distance, 
				event.name as eventname, coursenotes, event.seriesid  from event join course 
				on event.courseid=course.id order by event.eventdate;


create view v_ttentry as select entry.id, entry.eventid, 
				entry.userid, user.username, user.firstname,
				user.lastname, entry.signondate, entry.signonmethod, entry.handicap 
				from entry join user on entry.userid=user.id;

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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		