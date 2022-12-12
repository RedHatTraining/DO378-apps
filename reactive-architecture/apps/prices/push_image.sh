#!/usr/bin/env bash

REGISTRY="quay.io"
IMAGE="${REGISTRY}/redhattraining/do378-reactive-architecture-prices"

podman login quay.io

podman build -f Containerfile -t ${IMAGE} .
podman push ${IMAGE}
