#!/bin/sh

CMD="podman"

echo "Starting the PostgreSQL database container"
$CMD run -d --name postgresql-conference -p 5432:5432 \
  -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user \
  -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference \
  registry.access.redhat.com/rhscl/postgresql-10-rhel7:1 > /dev/null 2>&1
sleep 3
echo

echo "Starting the all-in-one Jaeger container"
$CMD run -d --name jaeger \
-p 5775:5775/udp \
-p 6831:6831/udp \
-p 6832:6832/udp \
-p 5778:5778 \
-p 16686:16686 \
-p 14268:14268 \
quay.io/jaegertracing/all-in-one:1.21.0 > /dev/null 2>&1
sleep 3
echo

echo "Starting the front end web application on port 8080"
$CMD run -d --name conference-frontend -p 8080:8080 quay.io/redhattraining/quarkus-conference-frontend:tracing > /dev/null 2>&1
sleep 3
echo

echo "Starting prometheus on port 9090"
$CMD run -d --name prometheus -p 9090:9090 --net host \
  -v "/home/student/DO378/labs/monitor-review/prometheus.yml:/etc/prometheus/prometheus.yml:Z" quay.io/prometheus/prometheus:v2.22.2 > /dev/null 2>&1
sleep 3
echo

echo "Starting grafana on port 3000"
$CMD run -d -p 3000:3000 --net host --name grafana \
  -v "/home/student/DO378/labs/monitor-review/datasource.yml:/etc/grafana/provisioning/datasources/datasource.yml:Z" \
  -v "/home/student/DO378/labs/monitor-review/dashboard_config.yml:/etc/grafana/provisioning/dashboards/dashboard_config.yml:Z" \
  -v "/home/student/DO378/labs/monitor-review/conference_dashboard.json:/etc/dashboards/conference/conference_dashboard.json:Z" \
  quay.io/redhattraining/do378-grafana:7.3.4 > /dev/null 2>&1
sleep 3
echo
read -p "Press enter to Terminate"
echo 
echo "Stopping all running containers..."
$CMD rm -f postgresql-conference conference-frontend jaeger prometheus grafana
sleep 3
echo
echo "All containers terminated"
echo
