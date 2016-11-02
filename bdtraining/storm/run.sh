#!/bin/sh

if [[ $# = 1  ]]; then
  echo "Should be in local mode"
  storm jar /var/tmp/bd/storm-samples-jar-with-dependencies.jar org.led.dbtraining.stormlog.ErrorTopology ErrorLogFilter $1
elif [[ $# = 2 ]]; then
  echo "Should be in kafka mode"
  storm jar /var/tmp/bd/storm-samples-jar-with-dependencies.jar org.led.dbtraining.stormlog.ErrorTopology ErrorLogFilter $1 $2
else
  echo "Usage: $ ./run.sh [local|kafka] <topic_name>"
fi
