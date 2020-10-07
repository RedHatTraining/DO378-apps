#! /bin/sh
# Utility script for executing the same command in multiple parallel threads.

COMMAND="$1"
THREADS=${2:-20}

echo
for i in $(seq 1 $THREADS); do 
    eval "$COMMAND" &
done; 
wait
