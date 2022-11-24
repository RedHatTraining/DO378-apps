#!/usr/bin/env bash

while :; do
    RESPONSE="$(curl -s localhost:8080/cpu/predict)"
    TRIMMED="$(echo ${RESPONSE} | sed 's/\(.\{50\}\).*/\1...\}/' )"

    echo $TRIMMED
    sleep 1
done
