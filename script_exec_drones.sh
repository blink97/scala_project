#!/bin/bash

nb=50

sbt clean compile 

echo "Starting drones"

for i in 1..nb
do
   sbt "runMain Client $i" &
done

echo "Done."
