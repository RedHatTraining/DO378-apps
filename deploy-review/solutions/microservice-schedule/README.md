# microservice schedule

Quarkus implementation of the Schedule microservice.

### Usage

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### Packaging and running the application

The application can be packaged using:

```
mvn package
```

It produces the quarkus-run.jar file in the target/quarkus-app/ directory. Be aware that it’s not an über-jar as the dependencies are copied into the target/quarkus-app/lib/ directory.

The application is now runnable using java -jar target/quarkus-app/quarkus-run.jar.

If you want to build an über-jar, execute the following command:

```
mvn package -Dquarkus.package.type=uber-jar
```

The application, packaged as an über-jar, is now runnable using java -jar target/*-runner.jar.

### Creating a native executable

You can create a native executable using:

```
mvn package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```
mvn package -Pnative -Dquarkus.native.container-build=true
```

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

### Deploy application on Openshift

Add the following dependency to the pom.xml file.

```
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-openshift</artifactId>
</dependency>
```

Execute the deployment process using:

```
mvn package -Dquarkus.kubernetes.deploy=true
```
