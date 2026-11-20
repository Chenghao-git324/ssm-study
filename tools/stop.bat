@echo off
rem ============================================================
rem  Stop Tomcat 9
rem  If the run.bat window is already closed, you rarely need this.
rem ============================================================

set "JAVA_HOME=C:\Users\ch2fx\.jdks\ms-17.0.20"
set "TOMCAT_HOME=C:\Users\ch2fx\dev-tools\apache-tomcat-9.0.123"
set "CATALINA_HOME=%TOMCAT_HOME%"

call "%TOMCAT_HOME%\bin\shutdown.bat"
echo [OK] stop command sent
pause
