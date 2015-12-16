#!/bin/bash

hdfs dfs -rm -r /input
hdfs dfs -mkdir /input
hdfs dfs -copyFromLocal pom.xml /input/
hdfs dfs -rm -r /output

hadoop jar target/word_count-0.0.1-SNAPSHOT.jar org.led.hadoop.word_count.WordCount /input /output

hdfs dfs -copyToLocal /output .
hdfs dfs -rm -r /output
