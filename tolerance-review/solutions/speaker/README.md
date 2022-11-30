# microservice speaker

Quarkus implementation of the Speakers management microservice.

## usage

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### running the application in dev mode

You can run your applications in dev mode that enables live coding using in every subproject module the next command:
```
./mvnw quarkus:dev
```

## description 

Quarkus communicates with Database [Apache Derby](https://db.apache.org/derby/)


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
