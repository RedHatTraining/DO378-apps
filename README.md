# DO378-apps

This repository contains the applications used in the [**Red Hat Cloud-native Microservices
Development with Quarkus**](https://www.redhat.com/en/services/training/red-hat-cloud-native-microservices-development-quarkus-do378) course.

## Note About Common Settings

The applications used in this course rely on the `~/.m2/settings.xml` file to configure common settings such as the Red Hat Build of Quarkus version and the location of the repositories.
Our settings file is as follows:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <profiles>
        <profile>
            <id>redhat-ga-all-repository</id>
            <repositories>
                <repository>
                    <id>redhat-ga-all-repository</id>
                    <name>Red Hat GA (all)</name>
                    <url>https://maven.repository.redhat.com/ga/</url>
                    <layout>default</layout>
                    <releases> 
                        <enabled>true</enabled>
                        <updatePolicy>never</updatePolicy>
                    </releases>
                    <snapshots>
                        <enabled>false</enabled>
                        <updatePolicy>never</updatePolicy>
                    </snapshots>
                </repository>
            </repositories>
            <pluginRepositories>
                <pluginRepository>
                    <id>redhat-ga-all-repository</id>
                    <name>Red Hat GA repository (all)</name>
                    <url>https://maven.repository.redhat.com/ga/</url>
                    <layout>default</layout>
                    <releases>
                        <enabled>true</enabled>
                        <updatePolicy>never</updatePolicy>
                    </releases>
                    <snapshots>
                        <enabled>false</enabled>
                        <updatePolicy>never</updatePolicy>
                    </snapshots>
                </pluginRepository>
            </pluginRepositories>
        </profile>
        <profile>
            <id>properties</id>
            <properties>
                <quarkus.platform.artifact-id>quarkus-bom</quarkus.platform.artifact-id>
                <quarkus.platform.group-id>com.redhat.quarkus.platform</quarkus.platform.group-id>
                <quarkus.platform.version>2.13.5.Final-redhat-00002</quarkus.platform.version>
            </properties>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>redhat-ga-all-repository</activeProfile>
        <activeProfile>properties</activeProfile>
    </activeProfiles>
</settings>
```