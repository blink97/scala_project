#!/bin/bash


COLOR_BLUE="\e[1m\e[34m"
COLOR_DEF="\e[39m"


echo -e "\n$COLOR_BLUE Executing : script-producer $COLOR_DEF\n"

cd kafka
sbt run
cd ..

