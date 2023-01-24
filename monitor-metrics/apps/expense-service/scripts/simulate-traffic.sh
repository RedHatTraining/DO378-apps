#!/bin/bash
ENDPOINT="http://localhost:8080/expenses"

curl -s -o /dev/null -w "GET Response Code: %{response_code}\n" --request GET "${ENDPOINT}"
curl -s -o /dev/null -w "GET Response Code: %{response_code}\n" --request GET "${ENDPOINT}"
curl -s -o /dev/null -w "GET Response Code: %{response_code}\n" --request GET "${ENDPOINT}"

curl -s -o /dev/null \
-w "POST Response Code: %{response_code}\n" \
--location --request POST "${ENDPOINT}" \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Podman in Action",
  "paymentMethod": "CASH",
  "amount": 33
}'
