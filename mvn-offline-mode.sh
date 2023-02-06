#!/bin/bash

# We fire multiple goals so we track dependencies that are only requested on those goals
# The ignore of tests is required because some apps will have incomplete tests
# Spoiler alert: this script might not be perfect!
find . -name "pom.xml" \
        -not -path "./quarkus-conference/*" \
        -not -path "./quarkus-calculator/*"  \
        -not -path "./quarkus-calculator-monolith/*" \
        -not -path "./istio-tutorial/*" \
        -execdir /opt/apache-maven/bin/mvn clean dependency:go-offline test -Dmaven.test.failure.ignore=true package \;
