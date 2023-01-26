# microservice speaker

Quarkus implementation of the Speakers management microservice.

## usage

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### running the application in dev mode

You can run your applications in dev mode that enables live coding using in every subproject module the next command:
```
mvn cpmpile quarkus:dev
```
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


### endpoints

This service provides a list of speakers, currently featured speakers for JavaOne 2016.

The endpoints are:

```
/speaker //<1>
/speaker/add //<2>
/speaker/remove/{id} //<3>
/speaker/update //<4>
/speaker/retrieve/{id} //<5>
/speaker/search //<6>
```
<1> List all
<2> Add a Speaker
<3> Remove a Speaker by id
<4> Update an existing Speaker
<5> Retrieve a known speaker
<6> Search for a speaker

```speaker.json
{
  "id" : "UUID",
  "nameFirst" : "Juan",
  "nameLast" : "Cierva",
  "organization" : "Puerta Esmeralda",
  "biography" : "A nobody",
  "picture" : "http://link/to/some.jpg",
  "twitterHandle" : "@JuanCierva"
}
```
