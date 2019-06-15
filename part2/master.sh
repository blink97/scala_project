#!/bin/sh

LOG_ZOOKEEPER="no"

TIMING_BIG=30
TIMING_SMALL=5
SCRIPTS_DIR="scripts"
PROJECT_JAR="scala_part2_2.12-0.1.jar"

COLOR_BLUE="\e[1m\e[34m"
COLOR_DEF="\e[39m"

# Automate Project run
# Execute all projects script

# Package jar
echo -e "\n$COLOR_BLUE Executing : sbt compile package $COLOR_DEF\n"
sbt compile package
cp target/scala-2.12/$PROJECT_JAR .

# zookeeper
echo -e "\n$COLOR_BLUE Executing : script-zoo-kafka $COLOR_DEF\n"
./$SCRIPTS_DIR/script-zoo-kafka.sh $LOG_ZOOKEEPER

sleep $TIMING_BIG

# Producer
echo -e "\n$COLOR_BLUE Executing : script-producer $COLOR_DEF\n"
./$SCRIPTS_DIR/script-producer.sh

sleep $TIMING_SMALL

# Consumer
echo -e "\n$COLOR_BLUE Executing : script-consumer $COLOR_DEF\n"
./$SCRIPTS_DIR/script-consumer.sh

sleep $TIMING_SMALL

# Done
echo -e "\n$COLOR_BLUE Executions : Done $COLOR_DEF\n"

