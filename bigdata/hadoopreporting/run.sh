#!/bin/sh

hdfs dfs -rm /input/*
hdfs dfs -copyFromLocal ~/tmp/reporting/*.avro /input/

hdfs dfs -rm -r /output

hadoop jar target/reporting-0.0.1-SNAPSHOT.jar org.led.hadoop.reporting.MainApp /input /output

hdfs dfs -copyToLocal /output .
hdfs dfs -rm -r /output
