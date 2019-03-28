# scala_project

## Start

```sh
sbt clean compile
sbt run
```

In the project root directory:

## sbt commands

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


## Web Endpoints


| Method | EndPoint | TYPE | Desc |
|--------|----------|------|------|
| GET    | /msg     | json array of msg objects | All drones message of the database |
| POST   | /msg     | json array | Post a message (drone to server usually) |
| GET    | /msg/drone/{id} | json array of msg objects | Give all messages of a drone |
| GET | /msg/removedronemsg/{id} | na | Remove all messages of the {id} drone |
| DELETE | /msg/removedronemsg/{id} | na | Remove all messages of the {id} drone |

* TODO finish list


## DataBase

* Table **msg** (all fields not null)

| col | type       |
|-----|------------|
| id  | SERIAL     |
| msg_id | INT |
| drone_id | INT |
| msg_type | CHAR(64) |
| temp | REAL (aka float) |
| time | TIMESTAMP |

* Table **geopos** (all fields not null)

| col | type       |
|-----|------------|
| id  | SERIAL     |
| msg_id | INT |
| x | INT |
| y | INT |
| alt | INT |
| time | TIMESTAMP |


