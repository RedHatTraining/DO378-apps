#!/bin/env bash

if [ $# -lt 2 ]; then
  echo 1>&2 "Usage: . $0 username password"
  echo 1>&2 "  available users (username/password):"
  echo 1>&2 "    user/redhat"
  echo 1>&2 "    superuser/redhat"
  exit 1
fi

SERVER="https://localhost:9999/realms/quarkus/protocol/openid-connect/token"
SECRET_ID="backend-service"
SECRET_PW="secret"
USERNAME="$1"
PASSWORD="$2"

export TOKEN=$(curl --insecure -s -X POST "$SERVER" \
  --user ${SECRET_ID}:${SECRET_PW} \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}" \
  -d 'grant_type=password' \
  | jq --raw-output '.access_token'
)

if [[ "$TOKEN" == "null" ]] || [[ "$TOKEN" == ""  ]]; then
    echo 1>&2 "Token was not retrieved! Review input parameters."
else
    echo 1>&2 "Token succesfuly retrieved."
fi
