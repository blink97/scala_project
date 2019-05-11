# scala_project

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


```bash
sbt "runMain Client"
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

| col | type       |
|-----|------------|
| id  | SERIAL     |
| drone_id | INT |
| msg_type | CHAR(64) |
| temp | REAL (aka float) |
| time | TIMESTAMP (as CHAR(64)) |
| x | INT |
| y | INT |
| alt | INT |

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

