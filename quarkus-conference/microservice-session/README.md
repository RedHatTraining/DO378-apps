# microservice session

Quarkus implementation of the Sessions management microservice.


## how to run

The test are using H2 memory database, for normal execution is defined a connection 
to postgresql database, here is the command used for runnning the container.

```
podman run -d --name postgresql-conference -p 5432:5432 -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference centos/postgresql-10-centos7:latest
```

