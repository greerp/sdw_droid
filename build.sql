.open sdw.db
.read /data/data/com.uk.greer.sdwapp/sql/tables.sql
--delete from users;
--delete from entries;
--delete from events;
--delete from courses;
.mode csv
.import /data/data/com.uk.greer.sdwapp/sql/courses.csv courses
.import /data/data/com.uk.greer.sdwapp/sql/events.csv events
.import /data/data/com.uk.greer.sdwapp/sql/users.csv users
.import /data/data/com.uk.greer.sdwapp/sql/entries.csv entries
.quit


