rem @echo off
rem addMySqlJbdcDriver.cmd
rem -----------------------------------------------------------------
rem Fuegt einer bestehenden WildFly-Installation den MySQL JDBC 
rem Treiber hinzu. 
rem Achtung: WildFly muss gestartet sein! 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
%WILDFLY_HOME%\bin\jboss-cli --connect --file=addMySqlJdbcDriver.cli
 

