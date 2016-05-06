#!/bin/sh

mvn clean install
jar umf src/META-INF/MANIFEST.MF target/sreporting-0.0.1-SNAPSHOT.jar
go cp target/sreporting-0.0.1-SNAPSHOT.jar 200:/var/tmp/reporting
