# PART 2

**SCALA + SPARK + KAFKA**

## Install && Requirements

#### Requirements

* sbt
* Scala 2.12.8
* kafka-2.12-2.2.1
* bash
* Java 8
* Internet Connection


## Usage

Each of these scripts launch required part for the project
demonstration.

* Run Zookeeper Kafka (only once by computer boot)

```sh
./master.sh
```

* Run producer

```sh
./run_consumer.sh
```


* Run Consumer

```sh
./run_producer.sh
```

Result of spark streaming stored in *stream/*


## Components


```
Scala Producer of Drones Messages
  
  |
  |
  V

Kafka
  
  |
  |
  V

Spark Streaming
  
  |
  |
  V

Files in (stream/) (Not HDFS)

  |
  |
  V

FrontEnd/Backend (Web Site)
```

### Kafka

**1** topic : *"msg"*

### Spark



