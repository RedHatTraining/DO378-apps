endpoint=$(oc get route microservice-session -o jsonpath="{'http://'}{.spec.host}{'/sessions'}")

curl -w "\nResponse Code: %{response_code}\n\n" \
--location --request POST "${endpoint}" \
--header 'Content-Type: application/json' \
--data-raw '{
  "sessionTitle": "Deploying at the Edge",
  "speakerId":  1
}'
