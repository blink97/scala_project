#!/bin/sh

TOPIC="msg"
PRODUCER="drone.kafka.DroneProducer"
PROJECT_JAR="scala_part2_2.12-0.1.jar"
PORT=9092

# Run Scala Producer 
java -cp $PROJECT_JAR $PRODUCER 10000 $TOPIC localhost:$PORT &

