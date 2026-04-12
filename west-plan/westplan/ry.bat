@echo off
setlocal enabledelayedexpansion

set APP_NAME=ruoyi-admin.jar
set JVM_OPTS=-Dname=%APP_NAME% -Duser.timezone=Asia/Shanghai -Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

if "%~1"=="" goto usage
if /I "%~1"=="start" goto start
if /I "%~1"=="stop" goto stop
if /I "%~1"=="restart" goto restart
if /I "%~1"=="status" goto status
goto usage

:findpid
set PID=
for /f "tokens=1,2" %%a in ('jps -l ^| findstr /I %APP_NAME%') do (
  set PID=%%a
)
exit /b 0

:start
if not exist "%APP_NAME%" (
  echo [%APP_NAME%] not found in current directory: %CD%
  echo Build first, for example: mvn clean package -DskipTests
  exit /b 1
)
call :findpid
if defined PID (
  echo %APP_NAME% is already running. PID=!PID!
  exit /b 0
)
start "ruoyi-admin" javaw %JVM_OPTS% -jar "%APP_NAME%"
echo Start %APP_NAME% success.
exit /b 0

:stop
call :findpid
if not defined PID (
  echo %APP_NAME% is not running.
  exit /b 0
)
echo Stopping %APP_NAME% PID=!PID!
taskkill /f /pid !PID! >nul 2>nul
if errorlevel 1 (
  echo Failed to stop %APP_NAME%.
  exit /b 1
)
echo Stop %APP_NAME% success.
exit /b 0

:restart
call :stop
call :start
exit /b 0

:status
call :findpid
if defined PID (
  echo %APP_NAME% is running. PID=!PID!
) else (
  echo %APP_NAME% is not running.
)
exit /b 0

:usage
echo Usage: ry.bat ^<start^|stop^|restart^|status^>
exit /b 1
