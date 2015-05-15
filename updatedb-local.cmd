rem - Copy CSVs to emulator and and executes SQL 

path=c:\dev\Android\sdk\platform-tools;%path%

rem Copy files to emulator

del sdw.db
sqlite3 -init build-local.sql
copy sdw.db app\src\main\assets
