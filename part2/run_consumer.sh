#!/bin/bash


COLOR_BLUE="\e[1m\e[34m"
COLOR_DEF="\e[39m"


echo -e "\n$COLOR_BLUE Executing : script-consumer $COLOR_DEF\n"

cd spark
sbt run 
cd ..


