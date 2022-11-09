endpoint=$(oc get route expenses-service -o jsonpath="{'http://'}{.spec.host}{'/expenses'}")

curl -w "\nResponse Code: %{response_code}\n\n" \
--location --request POST "${endpoint}" \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "e2",
    "paymentMethod": "CREDIT_CARD",
    "amount": "1000"
}'