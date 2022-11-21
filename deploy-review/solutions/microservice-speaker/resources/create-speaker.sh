endpoint=$(oc get route microservice-speaker -o jsonpath="{'http://'}{.spec.host}{'/speakers'}")

curl -w "\nResponse Code: %{response_code}\n\n" \
--location --request POST "${endpoint}" \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Pablo",
  "organization": "Red Hat"
}'
