#!/bin/sh

java -cp ./lib/ -Djava.rmi.server.hostname=localhost -Djava.security.policy=java.policy -Djava.server.codebase=rmi-classes -jar ${project.ArtifactId}-${project.version}.jar