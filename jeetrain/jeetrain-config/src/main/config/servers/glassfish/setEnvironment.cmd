set GLASSFISH_HOME=Z:\tools\glassfish\glassfish4\glassfish
set GLASSFISH_INSTANCE_ROOT=Z:\Data\glassfish
set GLASSFISH_DEFAULT_INSTANCE_NAME=GF41_JEETRAIN_DEV
if [%1]==[] (
	echo Kein Instanzname angegeben: benutze voreingestellten Instanznamen %GLASSFISH_DEFAULT_INSTANCE_NAME%
	set GLASSFISH_INSTANCE_NAME=%GLASSFISH_DEFAULT_INSTANCE_NAME%
) else (
	set GLASSFISH_INSTANCE_NAME=%1
)
set GLASSFISH_INSTANCE_PATH=%GLASSFISH_INSTANCE_ROOT%\%GLASSFISH_INSTANCE_NAME%