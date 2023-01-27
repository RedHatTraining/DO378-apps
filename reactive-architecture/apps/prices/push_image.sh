#!/usr/bin/env bash

REGISTRY="registry.ocp4.example.com:8443"
IMAGE="${REGISTRY}/redhattraining/do378-reactive-architecture-prices"

podman login ${REGISTRY}

podman build -f Containerfile -t ${IMAGE} .
podman push ${IMAGE}
