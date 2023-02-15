#!/bin/sh

echo "Adding tracing extension to the 'solver' project "
cd ~/DO378/monitor-trace/solver
mvn quarkus:add-extension -Dextension="smallrye-opentracing"
cd ..

echo "Adding tracing extension to the 'adder' project "
cd ~/DO378/monitor-trace/adder
mvn quarkus:add-extension -Dextension="smallrye-opentracing"
cd ..

echo "Adding tracing extension to the 'multiplier' project "
cd ~/DO378/monitor-trace/multiplier
mvn quarkus:add-extension -Dextension="smallrye-opentracing"
cd ..

echo
