#!/bin/bash


LOG_ZOOKEEPER="no"
PROJECT_JAR_PROD="kafka_2.12-0.1.jar"
PROJECT_JAR_CONS="cons.jar"
TIMING_BIG=30
TIMING_SMALL=5
KAFKA_DIR="kafka_2.12-2.2.1/"
SCRIPTS_DIR="scripts"
PROJECT_JAR="scala_part2_2.12-0.1.jar"
COLOR_BLUE="\e[1m\e[34m"
COLOR_DEF="\e[39m"
N_PARTITIONS=4
N_REPLICATION_FACTOR=1
ZOOKEEPER_CONFIG_FILE="$KAFKA_DIR/config/zookeeper.properties"
KAFKA_CONFIG_FILE="$KAFKA_DIR/config/server.properties"

# Automate Project run
# Execute all projects script

echo -e "\n$COLOR_BLUE Executing : sbt compile package PRODUCER $COLOR_DEF\n"
cd kafka
sbt compile package
cp target/scala-2.12/$PROJECT_JAR_PROD ..
cd -

echo -e "\n$COLOR_BLUE Executing : zookeeper server start $COLOR_DEF\n"

./$KAFKA_DIR/bin/zookeeper-server-start.sh $ZOOKEEPER_CONFIG_FILE  &

sleep $TIMING_SMALL

echo -e "\n$COLOR_BLUE Executing : kafka server start  $COLOR_DEF\n"

./$KAFKA_DIR/bin/kafka-server-start.sh $KAFKA_CONFIG_FILE &

sleep $TIMING_SMALL

echo -e "\n$COLOR_BLUE Executing : kafka topics  $COLOR_DEF\n"

./$KAFKA_DIR/kafka-topics --zookeeper localhost:2181 --create --topic msg --replication-factor $N_REPLICATION_FACTOR --partitions $N_PARTITIONS & 

sleep $TIMING_SMALL

echo -e "\n$COLOR_BLUE Executing : script-producer $COLOR_DEF\n"

java -cp $PROJECT_JAR_PROD DroneMsgProducer localhost:9092 &


exit 0

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

