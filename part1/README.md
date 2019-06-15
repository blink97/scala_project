# scala_project

> 2nd Version with [Spark/Kafka link here](README_CLOUD.md)

## Steps

1. Start Database
2. Start Web Server
3. Execute `./script_exec_drones.sh`
4. Check http://localhost:8080/
5. Wait several minutes
6. Check final results on http://localhost:8080/


## Start Database

> WARNING! Require **postgresql**  (psql (PostgreSQL) 10.7 )

0. Install postgresql if need as super user!
1. Execute `db/postgres_databas_create.sh`
2. Check in your terminal:
    * `psql -h localhost -U scala scalaproject`
    * input password : `42scala`
    * type : `\d` + `ENTER`
    * You should see 1 table, and 1 sequence
```
scalaproject=> \d
             List of relations
 Schema |     Name      |   Type   | Owner 
--------+---------------+----------+-------
 public | msg           | table    | scala
 public | msg_id_seq    | sequence | scala
(4 rows)
```

## Compile Sources

Before running executables, build them with: `sbt compile` in the project directory


## Start WebServer

```bash
sbt "runMain WebServer"
```

Look for [http://localhost:8080/](http://localhost:8080/) to see the frontend visualisation website.



## Start Drones

Execute the bash script : **script_exec_drones.sh** : will run 50 drones over the data which is stored in db/drones-json-data
which was already generated with DroneDataGenerator.

```bash
./script_exec_drones.sh 
```

### Single Drone Execution

To start a drone with the id '1':
```bash
sbt "runMain Client 1"
```
A drone needs its data in order to work well, see part **Generate drone data**

## Generate Drone Data

To generate the data for i drones starting at '1':
```bash
sbt "runMain DataDroneGenerator i"
```


## Web Endpoints


| Method | EndPoint | TYPE | Desc |
|--------|----------|------|------|
| GET    | /msg     | json array of msg objects | All drones message of the database |
| POST   | /msg     | json array | Post a message (drone to server usually) |
| GET    | /msg/drone/{id} | json array of msg objects | Give all messages of a drone |
| GET | /msg/removedronemsg/{id} | na | Remove all messages of the {id} drone |
| DELETE | /msg/removedronemsg/{id} | na | Remove all messages of the {id} drone |


## DataBase

* Table **msg** (all fields not null)

Contains all messages of the database

| col | type       |
|-----|------------|
| id  | SERIAL     |
| drone_id | INT |
| msg_type | CHAR(32) |
| temp | REAL (aka float) |
| time | TIMESTAMP (as CHAR(32)) |
| x | INT |
| y | INT |
| alt | INT |

* Table **drone** : last drone status

| col | type       |
|-----|------------|
| drone_id | INT |
| last_msg | CHAR(32) |
| last_temp | REAL (aka float) |
| last_time | TIMESTAMP (as CHAR(32)) |
| last_x | INT |
| last_y | INT |
| last_alt | INT |


## Msg Status

| Msg CODE | Desc. |
|----------|-------|
| OK       | Everything is fine |
| HOT      | Drone is too hot |
| COLD     | Drone is to cold |
| ERROR    | Any internal problem |
| BATTERY  | Drone alert because of a low battery level |

## SBT Usage

### Start 

```sh
sbt clean compile
sbt run
```

In the project root directory:

### sbt commands

* compile

```sh
sbt compile
```

* run

```sh
sbt run
```

* ship and package the project

```sh
sbt package
```

* clean

```sh
sbt clean
```

# TODO

* Add Battery status
