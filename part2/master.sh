# !/bin/sh

TIMING_BIG=30
TIMING_SMALL=5

# Automate Project run
# Execute all projects script
./script-zoo-kafka.sh

sleep $TIMING_BIG

./script-producer.sh

sleep $TIMING_SMALL

./script-consumer.sh
