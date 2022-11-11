endpoint=$(oc get route expenses-service -o jsonpath="{'http://'}{.spec.host}{'/expenses'}")

output=$(curl -s "${endpoint}")

echo "${output}" | jq .
