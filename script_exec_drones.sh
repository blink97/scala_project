#!/bin/bash

sbt clean compile

echo "Starting drones"

for i in {1..30}
do
  echo "Exec Drone $i"
  sbt "runMain Client $i"
done

echo "Done."
