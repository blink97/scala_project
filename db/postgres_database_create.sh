#!/usr/bin/env bash

psql postgres -c "CREATE DATABASE scalaproject"
psql postgres -c "CREATE USER scala WITH ENCRYPTED PASSWORD '42scala'"
psql postgres -c "GRANT ALL PRIVILEGES ON DATABASE scalaproject TO scala"

# psql postgres -c "\c scalaproject"
# psql postgres -c "CREATE TABLE MSG(ID INT PRIMARY KEY NOT NULL, DRONE_ID INT NOT NULL, MSG_TYPE CHAR(64) NOT NULL);"
set -e # Stop script on error

PGPASSWORD=42scala psql -h localhost -U scala scalaproject <<EOF

DROP TABLE IF EXISTS msg;
CREATE TABLE msg (
ID SERIAL PRIMARY KEY NOT NULL,
DRONE_ID INT NOT NULL,
MSG_TYPE CHAR(64) NOT NULL,
TEMP REAL NOT NULL,
TIME CHAR(64) NOT NULL,
X INT NOT NULL,
Y INT NOT NULL,
ALT INT NOT NULL
);

EOF


# DROP TABLE IF EXISTS geopos;
# CREATE TABLE geopos (
# ID SERIAL PRIMARY  KEY NOT NULL,
# X INT NOT NULL,
# Y INT NOT NULL,
# ALT INT NOT NULL,
# TIME CHAR(64) NOT NULL
# );
