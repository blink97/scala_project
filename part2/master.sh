#!/bin/sh

TIMING_BIG=30
TIMING_SMALL=5
SCRIPTS_DIR="scripts"

# Automate Project run
# Execute all projects script

# Package jar
echo -e "Executing : sbt compile package"
sbt compile package

# zookeeper
echo -e "Executing : script-zoo-kafka"
./$SCRIPTS_DIR/script-zoo-kafka.sh

sleep $TIMING_BIG

# Producer
echo -e "Executing : script-producer"
./$SCRIPTS_DIR/script-producer.sh

sleep $TIMING_SMALL

# Consumer
echo -e "Executing : script-consumer"
./$SCRIPTS_DIR/script-consumer.sh
