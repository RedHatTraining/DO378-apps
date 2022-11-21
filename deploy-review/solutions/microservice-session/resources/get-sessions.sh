endpoint=$(oc get route microservice-session -o jsonpath="{'http://'}{.spec.host}{'/sessions'}")

output=$(curl -s "${endpoint}")

echo "${output}" | jq .
