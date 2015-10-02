@echo off
echo Updating ownership of database on emulator
%android_tools%\adb -s emulator-5554 shell "chmod 666 /data/data/com.uk.greer.sdwapp/databases/sdw.db"