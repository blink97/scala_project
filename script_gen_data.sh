#!/bin/bash

sbt clean compile

for i in {1..50}
do
  sbt "runMain DroneDataGenerator $i" &>/dev/null
  echo "Gen Data Start for $i"
done
