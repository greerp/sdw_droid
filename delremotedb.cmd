@echo off
echo About to remove SDW.DB on emulator.. ensure emulator is running or Ctrl-C to abort
pause
adb -s emulator-5554 shell rm /data/data/com.uk.greer.sdwapp/databases/sdw.db