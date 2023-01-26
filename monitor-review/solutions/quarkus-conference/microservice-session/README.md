# microservice session

Quarkus implementation of the Sessions management microservice.

## usage

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### running the application in dev mode

You can run your applications in dev mode that enables live coding using in every subproject module the next command:
```
mvn compile quarkus:dev

## Packaging and running the application

The application can be packaged using:
```shell script
mvn package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
mvn package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
```

## how to run

The test are using H2 memory database, for normal execution is defined a connection
to postgresql database, here is the command used for runnning the container.

```
podman run -d --name postgresql-conference -p 5432:5432 -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference registry.access.redhat.com/rhscl/postgresql-10-rhel7:1
```
To deploy a PostgreSQL instance in your OpenShfit instance, use the following command:
```
oc new-app postgresql:10 -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference
```
