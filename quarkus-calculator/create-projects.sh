#!/bin/sh

cd ~/DO378/labs/apps-connect

echo "Creating the 'adder' project "
mvn io.quarkus:quarkus-maven-plugin:1.3.4.Final-redhat-00004:create \
  -DprojectGroupId=com.redhat.training \
  -DprojectArtifactId=adder \
  -DplatformGroupId=com.redhat.quarkus \
  -DplatformVersion=1.3.4.Final-redhat-00004 \
  -DclassName="com.redhat.training.AdderResource" \
  -Dpath="/adder" \
  -Dextensions="rest-client"

echo "Removing the test folder for the 'adder' project"
rm -rf ./adder/src/test

echo "Creating the 'multiplier' project "
mvn io.quarkus:quarkus-maven-plugin:1.3.4.Final-redhat-00004:create \
  -DprojectGroupId=com.redhat.training \
  -DprojectArtifactId=multiplier \
  -DplatformGroupId=com.redhat.quarkus \
  -DplatformVersion=1.3.4.Final-redhat-00004 \
  -DclassName="com.redhat.training.MultiplierResource" \
  -Dpath="/multiplier" \
  -Dextensions="rest-client"

echo "Removing the test folder for the 'multiplier' project"
rm -rf ./multiplier/src/test

echo "All done."
