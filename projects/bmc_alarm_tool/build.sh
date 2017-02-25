#!/bin/sh

rm -f AlarmOperator.class
rm -f AlarmOperator.jar
javac -Xlint:unchecked -cp "lib/*" AlarmOperator.java
jar -cvfm AlarmOperator.jar manifest.mf AlarmOperator.class
cp AlarmOperator.jar alarm_operator/
cp alarm_definition.txt alarm_operator/
cp alarm_operator.py alarm_operator/
tar zcvf alarm_operator.tar.gz alarm_operator/
