#!/bin/bash

find . -name "pom.xml" \
        -not -path "./quarkus-conference/*" \
        -not -path "./quarkus-calculator/*"  \
        -not -path "./quarkus-calculator-monolith/*" \
        -not -path "./istio-tutorial/*" \
        -execdir mvn dependency:go-offline \;
