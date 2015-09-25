@echo off
rem - Builds SDW.DB and copies to emulator
echo Ensure emulator is running or Ctrl-C to abort
pause

path=c:\dev\Android\sdk\platform-tools;%path%

call updatedb-local.bat
call pushdb.bat
call chmod.bat

rem Copy files to emulator
rem adb push series.csv /data/data/com.uk.greer.sdwapp/sql/series.csv
rem adb push courses.csv /data/data/com.uk.greer.sdwapp/sql/courses.csv
rem adb push events.csv /data/data/com.uk.greer.sdwapp/sql/events.csv
rem adb push users.csv /data/data/com.uk.greer.sdwapp/sql/users.csv
rem adb push entries.csv /data/data/com.uk.greer.sdwapp/sql/entries.csv
rem adb push tables.sql /data/data/com.uk.greer.sdwapp/sql/tables.sql
rem adb push build-adb.sql /data/data/com.uk.greer.sdwapp/sql/build-adb.sql
rem adb -s emulator-5554 shell "cd /data/data/com.uk.greer.sdwapp/databases&&rm sdw.db&&echo|sqlite3 -init ../sql/build-adb.sql&&chmod 666 sdw.db"
rem adb -s emulator-5554 shell "rm /data/data/com.uk.greer.sdwapp/databases/sdw.db 2>/dev/null"
rem adb -s emulator-5554 shell "cd /data/data/com.uk.greer.sdwapp/databases&&sqlite3 -init ../sql/build-adb.sql"
rem adb -s emulator-5554 shell "chmod 666 /data/data/com.uk.greer.sdwapp/databases/sdw.db"
rem adb pull /data/data/com.uk.greer.sdwapp/databases/sdw.db app\src\main\assets
