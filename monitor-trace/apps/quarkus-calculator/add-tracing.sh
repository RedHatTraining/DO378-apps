#!/bin/sh

echo "Adding tracing extension to the 'solver' project "
cd ~/DO378/monitor-trace/solver
mvn quarkus:add-extension -Dextension="quarkus-smallrye-opentracing"
cd ..

echo "Adding tracing extension to the 'adder' project "
cd ~/DO378/monitor-trace/adder
mvn quarkus:add-extension -Dextension="quarkus-smallrye-opentracing"
cd ..

echo "Adding tracing extension to the 'multiplier' project "
mvn quarkus:add-extension -Dextension="quarkus-smallrye-opentracing"
cd ..

echo
