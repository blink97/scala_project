# !/bin/sh

TIMING_BIG=30
TIMING_SMALL=5
SCRIPTS_DIR="scripts"

# Automate Project run
# Execute all projects script

# Package jar
sbt compile package

# zookeeper
./$SCRIPTS_DIR/script-zoo-kafka.sh

sleep $TIMING_BIG

# Producer
./$SCRIPTS_DIR/script-producer.sh

sleep $TIMING_SMALL

# Consumer
./$SCRIPTS_DIR/script-consumer.sh
