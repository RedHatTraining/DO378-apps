#!/bin/bash

DIR="src/test/resources/k8s-sb/testing"

# Create the testing dir if it does not exist
mkdir -p "${DIR}"

# Create properties

echo "testing"    > "${DIR}/database"
echo "localhost"  > "${DIR}/host" 
echo "developer"  > "${DIR}/password"
echo "5432"       > "${DIR}/port"
echo "postgresql" > "${DIR}/type"
echo "developer"  > "${DIR}/username"
