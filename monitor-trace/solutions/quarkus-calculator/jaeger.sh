#!/bin/sh

echo "Starting the all-in-one Jaeger container "
podman run --rm --name jaeger \
-p 5775:5775/udp \
-p 6831:6831/udp \
-p 6832:6832/udp \
-p 5778:5778 \
-p 16686:16686 \
-p 14268:14268 \
quay.io/redhattraining/do378-jaeger-allinone:1.41

