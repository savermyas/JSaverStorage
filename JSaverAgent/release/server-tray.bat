rem set CLASSPATH=./lib/derbytools.jar;./lib/derby.jar;./bin
rem cd bin
java -cp ./bin -Djava.server.hostname=172.16.136.17 -Djava.security.policy=java1.policy -Djava.server.codebase=stubs -jar JSaverAgent.jar
rem java -Xmx1G vstu.edu.ru.agent.TrayRunner
rem start wscript.exe ./bin/trayrunner.vbs -b