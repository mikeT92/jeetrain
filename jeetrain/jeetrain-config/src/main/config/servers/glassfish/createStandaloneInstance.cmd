rem @echo off
rem createStandaloneInstance.cmd
rem -----------------------------------------------------------------
rem Erstellt eine neue Glassfish-Standalone-Instanz. 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd %1
echo Erstelle nun die Glassfish-Instanz %GLASSFISH_INSTANCE_NAME%.
rem %GLASSFISH_HOME%\bin\asadmin create-domain --portbase 10000 --domaindir %GLASSFISH_INSTANCE_ROOT% %GLASSFISH_INSTANCE_NAME%
echo Starte die Glassfish-Instanz %GLASSFISH_INSTANCE_NAME%.
start /b %GLASSFISH_HOME%\bin\asadmin start-domain --domaindir %GLASSFISH_INSTANCE_ROOT% --watchdog true %GLASSFISH_INSTANCE_NAME%
echo Warte auf Server-Start
PING -n 31 127.0.0.1>nul 
echo Melde mich als Admin-User an
rem %GLASSFISH_HOME%\bin\asadmin --port 10048 login
echo Erstelle nun den JDBC-Connection-Pool
call %GLASSFISH_HOME%\bin\asadmin --port 10048 create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --steadypoolsize 0 --maxpoolsize 10 --maxwait 60 --poolresize 1 --idletimeout 300 --isolationlevel read-committed --isisolationlevelguaranteed true --isconnectvalidatereq true --validationmethod table --validationtable DUAL --failconnection true --allownoncomponentcallers false --nontransactionalconnections false --property user=root:password=rootLocal2015:databaseName=jeetrain_db:serverName=localhost:port=13306 JEETRAIN_POOL
echo Erstelle nun die JDBC-Ressource
call %GLASSFISH_HOME%\bin\asadmin --port 10048 create-jdbc-resource --connectionpoolid JEETRAIN_POOL jdbc/JEETRAIN_DATASOURCE
echo Stoppe die Glassfish-Instanz %GLASSFISH_INSTANCE_NAME%.
call %GLASSFISH_HOME%\bin\asadmin stop-domain --domaindir %GLASSFISH_INSTANCE_ROOT% %GLASSFISH_INSTANCE_NAME%
endlocal

