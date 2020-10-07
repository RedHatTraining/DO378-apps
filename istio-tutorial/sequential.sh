#! /bin/sh
COMMAND="$1"
SLEEP="${2:-3}"

while :; do 
    eval "$COMMAND"
    sleep 0.$((RANDOM % 3))
done