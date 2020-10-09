#!/bin/sh

##########  Utility function ################################

exit_if_error() {
  local exit_code=$1
  shift
  [[ $exit_code ]] &&                                    # do nothing if no error code passed
    ((exit_code != 0)) && {                              # do nothing if error code is 0
      printf 'ERROR: %s\n' "$@" >&2                      # we can use better logging here
      exit "$exit_code"                                  # we could also check to make sure error code is numeric when passed
    }
    kill $SCHEDULE_PID $SESSION_PID $SPEAKER_PID $VOTE_PID

}

##############################################################

echo "Starting the 'microservice-schedule' project "
cd microservice-schedule
mvn quarkus:dev & 
SCHEDULE_PID=$!
sleep 10
cd ..
read -p "Press enter to continue"

echo "Starting the 'microservice-session' project "
cd microservice-session
docker run -d --name postgresql-conference -p 5432:5432 -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference centos/postgresql-10-centos7:latest || exit_if_error $? "Failed to create postgress container."
sleep 5
mvn quarkus:dev & 
SESSION_PID=$!
sleep 10
cd ..
read -p "Press enter to continue"

echo "Starting the 'microservice-speaker' project "
cd microservice-speaker
mvn quarkus:dev & 
SPEAKER_PID=$!
sleep 10
cd ..
read -p "Press enter to continue"

echo "Starting the 'microservice-vote' project "
cd microservice-vote
docker run -d --name mongo-vote -p 27017:27017 mongo:4.0 || exit_if_error $? "Failed to create mongodb container."
mvn quarkus:dev & 
VOTE_PID=$!
sleep 10
cd ..
read -p "Press enter to continue"

echo "Starting the web application "
docker run -d --name conference-frontend -p 8080:8080 quay.io/redhattraining/quarkus-conference-frontend || exit_if_error $? "Failed to create frontend container."
WEBAPP_PID=$!
sleep 10
cd ..
read -p "Press enter to continue"

echo
read -p "Press enter to Terminate"
echo 
kill $SCHEDULE_PID $SESSION_PID $SPEAKER_PID $VOTE_PID 
sleep 4
echo "All services terminated"
echo