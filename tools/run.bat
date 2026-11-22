@echo off
rem Console must be UTF-8, otherwise Tomcat's UTF-8 logs show as garbage
chcp 65001 >nul
rem ============================================================
rem  Build springmvc-demo, then start Tomcat 9 on port 8081
rem  Usage: double-click this file
rem  Stop : close this window, or double-click stop.bat
rem ============================================================

set "JAVA_HOME=C:\Users\ch2fx\.jdks\ms-17.0.20"
set "TOMCAT_HOME=C:\Users\ch2fx\dev-tools\apache-tomcat-9.0.123"
set "CATALINA_HOME=%TOMCAT_HOME%"

rem Stop any Tomcat that is still running.
rem Otherwise port 8081 stays busy and mvn clean cannot delete target\ files.
call "%TOMCAT_HOME%\bin\shutdown.bat" >nul 2>&1
timeout /t 5 /nobreak >nul

cd /d "%~dp0..\springmvc-demo"
if errorlevel 1 (
    echo [ERROR] springmvc-demo folder not found
    pause
    exit /b 1
)

echo ============================================
echo  [1/2] Building springmvc-demo ...
echo ============================================
call mvn -q clean package war:exploded
if errorlevel 1 (
    echo.
    echo [ERROR] Build failed. Tomcat NOT started.
    pause
    exit /b 1
)
echo [OK] target\springmvc-demo-1.0-SNAPSHOT updated

echo.
echo ============================================
echo  [2/2] Starting Tomcat 9 on port 8081
echo        http://localhost:8081/springmvc-demo/hello
echo        Close this window to stop the server
echo ============================================
call "%TOMCAT_HOME%\bin\catalina.bat" run

pause
