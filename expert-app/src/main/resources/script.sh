#!/bin/sh

#CLASSPATH=./lib/derbytools.jar:./lib/derby.jar:./bin:./stubs:./
#export CLASSPATH
java -cp ./bin -Xmx256M -Djava.server.hostname=localhost -Djava.security.policy=java1.policy -Djava.server.codebase=stubs vstu.edu.ru.expert.ClientApplication