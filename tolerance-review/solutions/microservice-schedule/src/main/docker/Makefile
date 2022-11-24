# @(#) Makefile
#

# https://tech.davis-hansson.com/p/make/
SHELL := bash
.ONESHELL:
.SHELLFLAGS := -eu -o pipefail -c
# .DELETE_ON_ERROR:
MAKEFLAGS += --warn-undefined-variables
MAKEFLAGS += --no-builtin-rules

CONTAINER_TOOL:=$(if $(shell which podman), podman, docker)
DERBY_VERSION:=10.15.2.0
DERBY_IMAGE_NAME:=db-derby/openjdk-11-db-derby

.PHONY: build
build:
	$(CONTAINER_TOOL) build . -t $(DERBY_IMAGE_NAME):$(DERBY_VERSION) -t $(DERBY_IMAGE_NAME):latest

.PHONY: run
run:
	$(CONTAINER_TOOL) run -d --rm --name "db-derby" -p 1527:1527 $(DERBY_IMAGE_NAME)
