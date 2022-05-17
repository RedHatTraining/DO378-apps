# Microservice Vote

Session voting service. Vote for the your favorite session

### Create from plugin command

```
mvn io.quarkus:quarkus-maven-plugin:1.0.1.Final:create \
    -DprojectGroupId=org.acme.conference 
    -DprojectArtifactId=microservice-vote \
    -Dextensions="resteasy-jsonb" \
    -DclassName="org.acme.conference.vote.VoteResource" \
    -DoutputDirectory=.
```

### Running the app

```
git clone https://github.com/juazugas/quarkus-conference.git
cd microservice-vote
```

Prepare the mongodb database : 
[Running mongodb database](https://quarkus.io/guides/mongodb#running-a-mongodb-database)

```
podman run -d --name mongo-vote -p 27017:27017 mongo:4.0
```
or, if deploying to openshift:
```
oc new-app quay.io/redhattraining/do378-mongo:4.0
```


Run the application : 

```
mvnw compile quarkus:dev
```

## Endpoints

General information endpoints :

Access the URL <http://localhost:8080/swagger-ui>

```
GET    /health  # Provide health check of service
GET    /hello   # Returns description of service
```
```
$ http :8080/health
HTTP/1.1 200 OK
content-length: 46
content-type: application/json; charset=UTF-8

{
    "checks": [],
    "status": "UP"
}

$ http :8080/hello
HTTP/1.1 200 OK
Content-Length: 48
Content-Type: text/plain;charset=UTF-8

Hello from Microservice Session Vote Application

```

Attendee resource 

```
GET    /vote/attendee       # Create new attendee
POST   /vote/attendee       # Create new attendee
GET    /vote/attendee/{id}  # Get attendee by ID
POST   /vote/attendee/{id}  # Update existing attendee by ID
DELETE /vote/attendee/{id}  # Remove attendee by ID
```

```
$ http :8080/attendee name="John Clese"
HTTP/1.1 200 OK
Content-Length: 65
Content-Type: application/json

{
    "id": "abbbf61a-1695-4ef8-b72d-4e541dd95fb5",
    "name": "John Clese"
}

$ http put :8080/attendee/abbbf61a-1695-4ef8-b72d-4e541dd95fb5 name="John Cleese"
HTTP/1.1 200 OK
Content-Length: 66
Content-Type: application/json

{
    "id": "abbbf61a-1695-4ef8-b72d-4e541dd95fb5",
    "name": "John Cleese"
}

$ http :8080/attendee/abbbf61a-1695-4ef8-b72d-4e541dd95fb5
HTTP/1.1 200 OK
Content-Length: 66
Content-Type: application/json

{
    "id": "abbbf61a-1695-4ef8-b72d-4e541dd95fb5",
    "name": "John Cleese"
}

```

Rating resource

```
POST   /vote/rate           # Get a list of all session ratings
GET    /vote/rate           # Get list of all session ratings
PUT    /vote/rate/{id}      # Update session rating
GET    /vote/rate/{id}      # Get session rating by ID
DELETE /vote/rate/{id}      # Delete session rating by ID
```

```
$ http :8080/rate session=123-123-123 "attendeeId=a8de745e-9343-4259-9290-ba46a3db4140" rating=5
HTTP/1.1 200 OK
Content-Length: 120
Content-Type: application/json

{
    "attendeeId": "a8de745e-9343-4259-9290-ba46a3db4140",
    "id": "5e00e2fb4ebf1a6bddb97111",
    "rating": 5,
    "ratingId": "5e00e2fb4ebf1a6bddb97110",
    "session": "123-123-123"
}

$ http :8080/rate
HTTP/1.1 200 OK
Content-Length: 122
Content-Type: application/json

[
    {
        "attendeeId": "a8de745e-9343-4259-9290-ba46a3db4140",
        "id": "5e00e2fb4ebf1a6bddb97111",
        "rating": 5,
        "ratingId": "5e00e2fb4ebf1a6bddb97110",
        "session": "123-123-123"
    }
]

$ http put :8080/rate/5e00e2fb4ebf1a6bddb97110 session=123-123-123 "attendeeId=a8de745e-9343-4259-9290-ba46a3db4140" rating=4
HTTP/1.1 200 OK
Content-Length: 158
Content-Type: application/json

{
    "attendeeId": "a8de745e-9343-4259-9290-ba46a3db4140",
    "id": "5e00e2fb4ebf1a6bddb97111",
    "rating": 4,
    "ratingId": "5e00e2fb4ebf1a6bddb97110",
    "session": "123-123-123"
}

$ http  :8080/rate/5e00e2fb4ebf1a6bddb97110 
HTTP/1.1 200 OK
Content-Length: 158
Content-Type: application/json

{
    "attendeeId": "a8de745e-9343-4259-9290-ba46a3db4140",
    "id": "5e00e2fb4ebf1a6bddb97111",
    "rating": 4,
    "ratingId": "5e00e2fb4ebf1a6bddb97110",
    "session": "123-123-123"
}

 $ http delete  :8080/rate/5e00e2fb4ebf1a6bddb97110 
HTTP/1.1 204 No Content

```

Rating Statistics resource

```
GET    /vote/ratingsBySession         # Get all session votes by session ID
GET    /vote/averageRatingBySession   # Get average session rating by session ID
GET    /vote/ratingsByAttendee        # Get all votes made by attendee ID
```

```
$ http :8080/ratingsBySession?sessionId=123-123-123
HTTP/1.1 200 OK
Content-Length: 281
Content-Type: application/json

[
    {
        "attendeeId": "a8de745e-9343-4259-9290-ba46a3db4140",
        "id": "5e00d65a06596a5369a5fec6",
        "rating": 5,
        "session": "123-123-123"
    },
    {
        "attendeeId": "7383309d-4f09-44e8-bf5b-3ca2e4c28803",
        "id": "5e04ea0c64cf1558da6b11c5",
        "rating": 5,
        "ratingId": "5e04ea0c64cf1558da6b11c4",
        "session": "123-123-123"
    }
]

$ http :8080/averageRatingBySession?sessionId=123-123-123
HTTP/1.1 200 OK
Content-Length: 17
Content-Type: application/json

4.333333333333333

$ http :8080/ratingsByAttendee?attendeeId=7383309d-4f09-44e8-bf5b-3ca2e4c28803 
HTTP/1.1 200 OK
Content-Length: 478
Content-Type: application/json

[
    {
        "attendeeId": "7383309d-4f09-44e8-bf5b-3ca2e4c28803",
        "id": "5e04ea0c64cf1558da6b11c5",
        "rating": 5,
        "ratingId": "5e04ea0c64cf1558da6b11c4",
        "session": "123-123-123"
    },
    {
        "attendeeId": "7383309d-4f09-44e8-bf5b-3ca2e4c28803",
        "id": "5e04ea2764cf1558da6b11c7",
        "rating": 4,
        "ratingId": "5e04ea2764cf1558da6b11c6",
        "session": "123-123-124"
    }
]
```
