#!/bin/sh

TOPIC="msg"
CONSUMER="drone.kafka.DroneConsumer"
PROJECT_JAR="scala_part2_2.12-0.1.jar"
PORT=9092

# Run Scala Consumer
java -cp $PROJECT_JAR $CONSUMER localhost:$PORT group1 $TOPIC 10 0 &
