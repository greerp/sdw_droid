.open sdw.db
.read tables.sql
.mode csv
.headers on
.import course.csv course
.import event.csv event
.import user.csv user
.import entry.csv entry
.import series.csv series
.import official.csv official
delete from user where id='id';
delete from event where id='id';
delete from entry where id='id';
delete from series where id='id';
delete from official where id='id';




