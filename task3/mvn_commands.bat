@echo off
SET DEVELOPMENT_HOME=D:\WF_MP\week4

cd %DEVELOPMENT_HOME%\task2\web-project
call mvn clean compile
call mvn package
call mvn test
call mvn checkstyle:checkstyle
call mvn site
call mvn compile war:war
call mvn install 