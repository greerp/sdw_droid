rem - Copy CSVs to emulator and and executes SQL 

path=c:\dev\Android\sdk\platform-tools;%path%

rem Copy files to emulator
adb push courses.csv /data/data/com.uk.greer.sdwapp/sql/courses.csv
adb push events.csv /data/data/com.uk.greer.sdwapp/sql/events.csv
adb push users.csv /data/data/com.uk.greer.sdwapp/sql/users.csv
adb push entries.csv /data/data/com.uk.greer.sdwapp/sql/entries.csv
adb push tables.sql /data/data/com.uk.greer.sdwapp/sql/tables.sql
adb push build.sql /data/data/com.uk.greer.sdwapp/sql/build.sql

adb -s emulator-5554 shell "cd /data/data/com.uk.greer.sdwapp/databases&&rm sdw.db&&echo|sqlite3 -init ../sql/build.sql&&chmod 666 sdw.db"
adb pull /data/data/com.uk.greer.sdwapp/databases/sdw.db app\src\main\assets
