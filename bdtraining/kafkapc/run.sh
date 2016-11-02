#!/bin/sh

if [[ $# != 2 ]];then
   echo "Usage:  $ ./run.sh [p|c] <topic_name>"
   exit
fi

java -jar /var/tmp/bd/kafkalogpc-0.0.1-SNAPSHOT-jar-with-dependencies.jar $1 $2
