#!/bin/bash

# We fire multiple goals so we track dependencies that are only requested on those goals
# The ignore of tests is required because some apps will have incomplete tests
# Spoiler alert: this script might not be perfect!
cd /tmp && rm -rf tenther && /opt/apache-maven/bin/mvn \
    com.redhat.quarkus.platform:quarkus-maven-plugin:2.13.5.Final-redhat-00002:create \
        -DprojectGroupId=com.redhat.training \
        -DprojectArtifactId=tenther \
        -DplatformVersion=2.13.5.Final-redhat-00002

cd /home/student/DO378/DO378-apps

find . -name "pom.xml" \
        -not -path "./quarkus-conference/*" \
        -not -path "./quarkus-calculator/*"  \
        -not -path "./quarkus-calculator-monolith/*" \
        -not -path "./istio-tutorial/*" \
        -execdir /opt/apache-maven/bin/mvn clean dependency:go-offline test -Dmaven.test.failure.ignore=true package \;
