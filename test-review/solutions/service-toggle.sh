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

function toggleService(){
  SERVICE_NAME=$1
  PID_VARIABLE=$2

  if [ -z "${!PID_VARIABLE}" ]; then
    # no pid, start service
    echo "Starting $SERVICE_NAME"
    cd microservice-$SERVICE_NAME
    mvn quarkus:dev -DskipTests & 
    sleep 15
    eval $PID_VARIABLE=$(ps -fa | grep -v grep | grep $SERVICE_NAME | grep "quarkus:dev" | awk '{printf $2}')
    echo "Service $SERVICE_NAME started"
    cd ..
  else 
    # have pid, terminate service
    echo "Stopping $SERVICE_NAME (${!PID_VARIABLE}) "
    kill -1 ${!PID_VARIABLE} 2>/dev/null
    eval $PID_VARIABLE=""
    sleep 3
    echo "Service $SERVICE_NAME stopped"
  fi
  # read -p "Press enter to continue (${!PID_VARIABLE})"
}

function toggleContainer(){
  SERVICE_NAME=$1
  PID_VARIABLE=$2
  RUN_COMMAND=$3

  if [ -z "${!PID_VARIABLE}" ]; then
    # no pid, start service
    echo "Starting $SERVICE_NAME"
    podman rm -f "$SERVICE_NAME" 
    eval $PID_VARIABLE=$(podman run -d --name "$SERVICE_NAME" $RUN_COMMAND)
    echo "Service $SERVICE_NAME started"
  else 
    # have pid, terminate service
    echo "Stopping $SERVICE_NAME"
    podman rm -f "$SERVICE_NAME"
    eval $PID_VARIABLE=""
    echo "Service $SERVICE_NAME stopped"
  fi
  # read -p "Press enter to continue (${!PID_VARIABLE})"

}

function status(){
    echo "PostgreSQL: $POSTGRESQL_PID"
    echo "Session: $SESSION_PID"
    echo "Schedule: $SCHEDULE_PID"
    echo "Speaker: $SPEAKER_PID"
    echo "Mongo: $MONGO_PID"
    echo "Vote: $VOTE_PID"
    echo "Frontend: $FRONTEND_PID"
}

##############################################################

PS3="Choose an option: "
options=("Session's PostgreSQL" "Session" "Schedule" "Speaker" "Votes's MongoDB" "Vote"  "Frontend"  "All" "Status" "Exit")
select menu in "${options[@]}";
do
  case $menu  in
        "Session's PostgreSQL")
          toggleContainer "postgresql" "POSTGRESQL_PID" "-p 5432:5432 -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference registry.access.redhat.com/rhscl/postgresql-10-rhel7:1"
            ;;
        "Session")
          toggleService "session" "SESSION_PID"
            ;;
        "Schedule")
          toggleService "schedule" "SCHEDULE_PID"
            ;;
        "Speaker")
          toggleService "speaker" "SPEAKER_PID"
            ;;
        "Votes's MongoDB")
          toggleContainer "mongo" "MONGO_PID" "-p 27017:27017 quay.io/redhattraining/do378-mongo:4.0"
            ;;            
        "Vote")
          toggleService "vote" "VOTE_PID"
            ;;
        "Frontend")
          toggleContainer "frontend" "FRONTEND_PID" "-p 8080:8080 quay.io/redhattraining/quarkus-conference-frontend"
            ;;
        "All")
          toggleContainer "postgresql" "POSTGRESQL_PID" "-p 5432:5432 -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference registry.access.redhat.com/rhscl/postgresql-10-rhel7:1"
          toggleService "session" "SESSION_PID"
          toggleService "schedule" "SCHEDULE_PID"
          toggleService "speaker" "SPEAKER_PID"
          toggleContainer "mongo" "MONGO_PID" "-p 27017:27017 quay.io/redhattraining/do378-mongo:4.0"
          toggleService "vote" "VOTE_PID"
          toggleContainer "frontend" "FRONTEND_PID" "-p 8080:8080 quay.io/redhattraining/quarkus-conference-frontend"

          read -p "Press enter to continue"
          status
            ;;
        "Status")
            status
            ;;
        "Exit")
          exit;
          ;;
        *)
          echo "What?!"
        ;;
  esac 
done



# echo "Starting the 'microservice-schedule' project "
# cd microservice-schedule
# mvn quarkus:dev & 
# SCHEDULE_PID=$!
# sleep 10
# cd ..
# read -p "Press enter to continue"

# echo "Starting the 'microservice-session' project "
# cd microservice-session
# docker run -d --name postgresql-conference -p 5432:5432 -e POSTGRESQL_PASSWORD=confi_user -e POSTGRESQL_USER=conference_user -e POSTGRESQL_ADMIN_PASSWORD=conference -e POSTGRESQL_DATABASE=conference registry.access.redhat.com/rhscl/postgresql-10-rhel7:1 || exit_if_error $? "Failed to create postgress container."
# sleep 5
# mvn quarkus:dev & 
# SESSION_PID=$!
# sleep 10
# cd ..
# read -p "Press enter to continue"

# echo "Starting the 'microservice-speaker' project "
# cd microservice-speaker
# mvn quarkus:dev & 
# SPEAKER_PID=$!
# sleep 10
# cd ..
# read -p "Press enter to continue"

# echo "Starting the 'microservice-vote' project "
# cd microservice-vote
# docker run -d --name mongo-vote -p 27017:27017 mongo:4.0 || exit_if_error $? "Failed to create mongodb container."
# mvn quarkus:dev & 
# VOTE_PID=$!
# sleep 10
# cd ..
# read -p "Press enter to continue"

# echo "Starting the web application "
# docker run -d --name conference-frontend -p 8080:8080 quay.io/redhattraining/quarkus-conference-frontend || exit_if_error $? "Failed to create frontend container."
# WEBAPP_PID=$!
# sleep 10
# cd ..
# read -p "Press enter to continue"

# echo
# read -p "Press enter to Terminate"
# echo 
# kill $SCHEDULE_PID $SESSION_PID $SPEAKER_PID $VOTE_PID 
# docker rm -f conference-frontend mongo-vote postgresql-conference
# sleep 4
# echo "All services terminated"
# echo


