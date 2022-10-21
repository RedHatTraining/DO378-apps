# microservice-speaker Project

Quarkus implementation of a Speakers management microservice.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

## Packaging the application

The application can be packaged using:
```shell script
mvn package
```

## Running the application

You can run your applications in dev mode that enables live coding using in every subproject module the next command:
```
mvn quarkus:dev
```

## Creating a native executable

You can create a native executable using:
```shell script
mvn package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
mvn package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/microservice-speaker-1.0.0-SNAPSHOT-runner`
