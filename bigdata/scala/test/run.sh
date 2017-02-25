#!/bin/bash

rm -rf *.class
javac ShellExecutor.java
#java ShellExecutor

scalac -classpath . test.scala
scala -classpath . Test
