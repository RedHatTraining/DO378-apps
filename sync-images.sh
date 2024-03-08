#!/bin/bash

# Based on: https://www.redhat.com/sysadmin/moving-openstack-containers

images=('quay.io/keycloak/keycloak:20.0')
images+=('quay.io/prometheus/prometheus:v2.41.0')
images+=('quay.io/quarkus/quarkus-micro-image:1.0')
images+=('quay.io/redhattraining/ad221-kafka:0.26.0-kafka-3.0.0')
images+=('quay.io/redhattraining/ad221-mysql:5.7')
images+=('quay.io/redhattraining/do378-grafana:9.3.2')
images+=('quay.io/redhattraining/do378-kafka:0.26.0-kafka-3.0.0')
images+=('quay.io/redhattraining/do378-mandrel-21-jdk17-rhel8:21.3')
images+=('quay.io/redhattraining/do378-postgres:14.1')
images+=('quay.io/redhattraining/do378-reactive-architecture-prices')
images+=('quay.io/redhattraining/do378-jaeger-allinone:1.21.0')
images+=('quay.io/redhattraining/quarkus-speaker-dashboard:latest')
images+=('quay.io/redhattraining/do378-redpanda:v22.3.4')
images+=('registry.access.redhat.com/quarkus/mandrel-22-rhel8:22.3')
images+=('registry.access.redhat.com/rhscl/postgresql-10-rhel7:latest')
images+=('registry.access.redhat.com/rhscl/postgresql-10-rhel7:1')
# images+=('registry.access.redhat.com/ubi8/openjdk-11:latest')
# images+=('registry.access.redhat.com/ubi8/openjdk-11:1.11')
# images+=('registry.access.redhat.com/ubi8/openjdk-17:1.11')
images+=('registry.access.redhat.com/ubi8/openjdk-17:1.14')
images+=('registry.access.redhat.com/ubi8/openjdk-17:1.18')
images+=('registry.access.redhat.com/ubi8/ubi-minimal:8.1')
images+=('registry.access.redhat.com/ubi8/ubi-minimal:8.5')
images+=('registry.access.redhat.com/ubi8/ubi-minimal:8.6')
images+=('registry.access.redhat.com/ubi9/python-39:latest')
images+=('registry.developers.crunchydata.com/crunchydata/crunchy-pgbackrest:ubi8-2.38-0')
images+=('registry.developers.crunchydata.com/crunchydata/crunchy-postgres:ubi8-14.2-1')
#images+=('registry.redhat.io/openjdk/openjdk-11-rhel8:latest')

quay_registry='quay.io'
rh_registry='registry.access.redhat.com'
rh_io_registry='registry.redhat.io'
crunchy_registry='registry.developers.crunchydata.com'

internal_registry='registry.ocp4.example.com:8443'

retry() {
    local -r -i max_attempts="$1"; shift
    local -r cmd="$@"
    local -i attempt_num=1
    until $cmd
    do
        if ((attempt_num==max_attempts))
        then
            echo "Attempt $attempt_num failed and there are no more attempts left!"
            return 1
        else
            echo "Attempt $attempt_num failed! Trying again in $attempt_num seconds..."
            sleep $((attempt_num++))
        fi
    done
}

for image in "${images[@]}"; do
  new_image="${image/${quay_registry}/${internal_registry}}"
  new_image="${new_image/${rh_registry}/${internal_registry}}"
  new_image="${new_image/${rh_io_registry}/${internal_registry}}"
  new_image="${new_image/${crunchy_registry}/${internal_registry}}"


  echo "${image} -> ${new_image}"

  copy="skopeo copy docker://${image} docker://${new_image} --remove-signatures"
  retry 5 $copy

  skopeo
done
