#!/bin/sh


#=============Generate java code for avro file==========
#$ java -jar /opt/apache-avro/default/avro-tools-1.7.6.jar compile schema ./common/src/main/resources/receptionreport.avsc .
#=============de-serialize an avro file============
#$ java -jar /opt/apache-avro/default/avro-tools-1.7.6.jar tojson test.avro > test.json
#=============get schema from avro file===========
#$ java -jar /opt/apache-avro/default/avro-tools-1.7.6.jar getschema test.avro > test.avsc
#=============serialize a json file===============
#$ java -jar /opt/apache-avro/default/avro-tools-1.7.6.jar fromjson --schema-file test.avsc test.json


#java -jar /opt/apache-avro/default/avro-tools-1.7.6.jar compile schema receptionreport.avsc src/main/java/
mvn clean install
jar umf src/META-INF/MANIFEST.MF target/sreporting-0.0.1-SNAPSHOT.jar
go cp target/sreporting-0.0.1-SNAPSHOT.jar 200:/var/tmp/reporting

echo "/opt/spark/default/bin/spark-submit --class \"org.led.sreporting.MainApp\" --master local[4] sreporting-0.0.1-SNAPSHOT.jar 2>&1 | grep ==="
