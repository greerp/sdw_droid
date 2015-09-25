/*.open sdw.db*/
.read ../sql/tables.sql
.mode csv
.headers on
.import ../sql/courses.csv courses
.import ../sql/events.csv events
.import ../sql/users.csv users
.import ../sql/entries.csv entries
.import ../sql/series.csv series
delete from users where id='id';
delete from events where id='id';
delete from entries where id='id';
delete from series where id='id';
.save sdw.db
.quit



