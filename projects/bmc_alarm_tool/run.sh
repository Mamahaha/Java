#!/bin/sh

#./build.sh
#rm -f file.log
#java -cp .:./lib/* -jar AlarmOperator.jar raise BMC121 SDCH322 1527 "database_dfadfdasdfaccess_failure_alarm."
./alarm_operator.py raise vvv aaa 449153262 minor "Setting is missing sa service/class."
#cat file.log
