# !/bin/sh

TIMING=5
KAFKA_DIR="./kafka_2.12-2.2.1/"
TOPIC="msg"
ZOOKEEPER_CONFIG_FILE="$KAFKA_DIR/config/zookeeper.properties"
KAFKA_CONFIG_FILE="$KAFKA_DIR/config/server.properties"
PORT_1=2181
N_PARTITIONS=10
N_REPLICATION_FACTOR=1

# Start Zookeeper
echo -e "Start ZooKeeper with config at $ZOOKEEPER_CONFIG_FILE"

./$KAFKA_DIR/bin/zookeeper-server-start.sh $ZOOKEEPER_CONFIG_FILE

# Timing
sleep $TIMING

# Start Kafka
echo -e "Start Kafka with config at $KAFKA_CONFIG_FILE"

./$KAFKA_DIR/bin/kafka-server-start.sh $KAFKA_CONFIG_FILE

# Timing
sleep $TIMING


# Create Topic
./$KAFKA_DIR/bin/kafka-topics.sh --create --zookeeper localhost:$PORT_1 \
 --replication-factor $N_REPLICATION_FACTOR --partitions $N_PARTITIONS --topic $TOPIC




