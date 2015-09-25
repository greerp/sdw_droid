.open sdw.db
.read tables.sql
.mode csv
.headers on
.import courses.csv courses
.import events.csv events
.import users.csv users
.import entries.csv entries
.import series.csv series
delete from users where id='id';
delete from events where id='id';
delete from entries where id='id';
delete from series where id='id';




