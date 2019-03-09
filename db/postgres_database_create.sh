

psql postgres -c "CREATE DATABASE test42"
psql postgres -c "CREATE USER scala WITH ENCRYPTED PASSWORD '42scala'"
psql postgres -c "GRANT ALL PRIVILEGES ON DATABASE test42 TO scala"

# psql postgres -c "\c test42"
# psql postgres -c "CREATE TABLE MSG(ID INT PRIMARY KEY NOT NULL, DRONE_ID INT NOT NULL, MSG_TYPE CHAR(64) NOT NULL);"
set -e # Stop script on error

PGPASSWORD=42scala psql -h localhost -U scala test42 <<EOF

DROP TABLE IF EXISTS lol;
CREATE TABLE lol (ID SERIAL);
EOF

