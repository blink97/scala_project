#!/bin/bash

nb=50

sbt clean compile

sbt "runMain WebServer" &>/dev/null &

for i in 1..nb
do
   sbt "runMain DroneDataGenerator $i" &>/dev/null
done