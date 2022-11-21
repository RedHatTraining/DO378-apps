endpoint=$(oc get route microservice-speaker -o jsonpath="{'http://'}{.spec.host}{'/expenses'}")

output=$(curl -s "${endpoint}")

echo "${output}" | jq .
