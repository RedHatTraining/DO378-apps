#!/bin/sh

echo "Starting the all-in-one Jaeger container "
podman run --rm --name jaeger \
  -p 4317:4317 \
  -p 4318:4318 \
  -p 16686:16686 \
  -p 14268:14268 \
registry.ocp4.example.com:8443/redhattraining/do378-jaeger-allinone:1.55.0

