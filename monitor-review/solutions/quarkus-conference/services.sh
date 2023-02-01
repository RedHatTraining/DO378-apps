#!/bin/sh

cd /home/student/DO378/solutions/monitor-review

echo "Starting the 'microservice-session' project on port 8081"
cd microservice-session
mvn clean quarkus:dev -Ddebug=5005 &
SESSION_PID=$!
sleep 10
echo
cd ..

echo "Starting the 'microservice-speaker' project on port 8082"
cd microservice-speaker
mvn clean quarkus:dev -Ddebug=5006 &
SPEAKER_PID=$!
sleep 10
echo
cd ..

echo
read -p "Press enter to Terminate"
echo 
echo "Stopping all Quarkus services..."
kill $SESSION_PID $SPEAKER_PID
sleep 3
echo
echo "All services terminated"
echo
