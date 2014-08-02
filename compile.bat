@ECHO OFF

IF "%1"=="-r" sbt ;clean;package;run
IF "%1"=="-c" sbt ~;clean;package;
IF "%1"=="" sbt ;clean;package