#!/bin/sh

#java -jar /opt/apache-avro/default/avro-tools-1.7.6.jar compile schema receptionreport.avsc src/main/java/
mvn clean install
jar umf src/META-INF/MANIFEST.MF target/sreporting-0.0.1-SNAPSHOT.jar
go cp target/sreporting-0.0.1-SNAPSHOT.jar 200:/var/tmp/reporting

echo "/opt/spark/default/bin/spark-submit --class \"org.led.sreporting.MainApp\" --master local[4] sreporting-0.0.1-SNAPSHOT.jar 2>&1 | grep ==="
