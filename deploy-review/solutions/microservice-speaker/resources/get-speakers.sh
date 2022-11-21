endpoint=$(oc get route microservice-speaker -o jsonpath="{'http://'}{.spec.host}{'/speakers'}")

output=$(curl -s "${endpoint}")

echo "${output}" | jq .
