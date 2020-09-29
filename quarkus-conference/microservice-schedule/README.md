mvn io.quarkus:quarkus-maven-plugin:1.0.1.Final:create     -DprojectGroupId=org.acme.conference     -DprojectArtifactId=microservice-schedule     -DclassName="org.acme.conference.schedule.ScheduleResource"     -Dpath="/schedule"     -Dextensions="resteasy-jsonb"

## Run derby database in local host

Go to `src/main/docker`

Build the Derby image ...

`podman build ...` or `make build`

Run the Derby image listening in 1527 port.

`podman run -p 1527:1527 db-derby` or `make run`


