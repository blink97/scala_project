#!/bin/bash

sbt clean compile

sbt "runMain DroneDataGenerator 50"

# for i in {1..50}
# do
  # sbt "runMain DroneDataGenerator $i" &
  # echo "Gen Data Start for $i"
# done

