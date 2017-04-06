JSaverStorage
=============

A system that allows to store dispersed files, migrated from https://sourceforge.net/projects/jsaverstorage/


Build
=====

    mvn clean package



Run
===


Agent application
-----------------

Go to its build folder

    cd agent-app/target

and run

    sh server-tray.sh

for Linux or

    server-tray.bat

for Windows system.


Expert application
------------------

Go to its build folder

    cd expert-app/target

adjust the coordinates of all available agents in 'xmlconfig.xml' file and then run

    sh start.sh

for Linux or

    start.bat

for Windows system.