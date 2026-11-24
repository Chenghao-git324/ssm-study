@echo off
chcp 65001 >nul
rem ============================================================
rem  Build the multi-module project (ssm-modules) and start Tomcat 9
rem  Usage: double-click this file
rem  URL  : http://localhost:8081/ssm-web/
rem ============================================================

set "JAVA_HOME=C:\Users\ch2fx\.jdks\ms-17.0.20"
set "TOMCAT_HOME=C:\Users\ch2fx\dev-tools\apache-tomcat-9.0.123"
set "CATALINA_HOME=%TOMCAT_HOME%"

rem Stop any Tomcat that is still running.
call "%TOMCAT_HOME%\bin\shutdown.bat" >nul 2>&1
timeout /t 5 /nobreak >nul

cd /d "%~dp0..\ssm-modules"
if errorlevel 1 (
    echo [ERROR] ssm-modules folder not found
    pause
    exit /b 1
)

echo ============================================
echo  [1/2] Building all modules ...
echo ============================================
call mvn -q clean install
if errorlevel 1 (
    echo.
    echo [ERROR] Build failed. Tomcat NOT started.
    pause
    exit /b 1
)

echo [OK] modules installed

echo.
echo ============================================
echo  [2/2] Exploding ssm-web ...
echo ============================================
call mvn -q -pl ssm-web war:exploded
if errorlevel 1 (
    echo.
    echo [ERROR] war:exploded failed.
    pause
    exit /b 1
)

echo [OK] ssm-web\target\ssm-web updated
echo.
echo ============================================
echo  Starting Tomcat 9 on port 8081
echo  http://localhost:8081/ssm-web/
echo  Close this window to stop the server
echo ============================================
call "%TOMCAT_HOME%\bin\catalina.bat" run

pause
