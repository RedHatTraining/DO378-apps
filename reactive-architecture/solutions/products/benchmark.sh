#! /bin/sh
# Utility script for executing the same command in multiple parallel threads.

COMMAND="curl -s http://localhost:8080/products/1/priceHistory > /dev/null"
REQUESTS=${1:-10}

echo
for i in $(seq 1 ${REQUESTS}); do
    echo "Sending request..."
    eval "${COMMAND}" &
    sleep 0.1
done;
wait