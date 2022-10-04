# microservice-speaker Project

Quarkus implementation of the Speakers management microservice.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging the application

The application can be packaged using:
```shell script
./mvnw package
```

## Running the application

You can run your applications in dev mode that enables live coding using in every subproject module the next command:
```
./mvnw quarkus:dev
```

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/microservice-speaker-1.0.0-SNAPSHOT-runner`


### endpoints 

This service provides a list of speakers, currently featured speakers for JavaOne 2016.

The endpoints are:

```
/speaker //<1>
/speaker/sorted //<2>
/speaker/{uuid} //<3>
/speaker/search //<4>
/speaker/add //<5>
/speaker/update/{uuid} //<6>
/speaker/delete/{uuid} //<7>
/speaker/paging //<8>
```
<1> List all
<2> List speakers in sorted manner
<3> Search a Speaker by id
<4> Search a speaker by Query
<5> Add a new Speaker
<6> Update a Speaker by id
<7> Delete a Speaker by id
<8> List all pages

##  Sample curl requests

curl `http://localhost:8082/speaker`
curl `http://localhost:8082/speaker/sorted`
curl `http://localhost:8082/speaker/s-1-2`
