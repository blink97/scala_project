#!/bin/bash

sbt clean compile

echo "Starting drones"

for i in {1..50}
do
  echo "Exec Drone $i"
  sbt "runMain Client $i"
done

echo "Done."
