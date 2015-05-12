if "%1"=="" goto end

set currentdir=%cd%
cd C:\Dev\Android\sdk\platforms\android-21\data\res
echo.
for /d %%x in (*.*) do if exist "%%x\%1" copy "%%x\%1" "%currentdir%\app\src\main\res\%%x\%1"
cd %currentdir%
