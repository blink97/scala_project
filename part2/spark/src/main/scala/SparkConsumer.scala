import java.time.{LocalDate, Period}

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.StreamingContext._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DataTypes, StructType}

object SparkConsumor {
  def main(args: Array[String]): Unit = {
    new SparkConsumor("localhost:9092").process()
  }
}

class SparkConsumor(brokers: String) {
  def process(): Unit = {
    // Init Saprk Conf
    val conf = new SparkConf()
      .setAppName("spark")
      .setMaster("local[*]")
      .getOrCreate()

    val ssc = new StreamingContext(conf, Seconds(5))


    val topic = "msg"
    val zkhost = "localhost"
    val zkports = "2181"

    val nbReceivers = 1

    val kafkaProperties: Map[String, String] = 
      Map("zookeeper.hosts" -> zkhost,
          "zookeeper.port" -> zkports,
          "kafka.topic" -> topic,
          "zookeeper.consumer.connection" -> "localhost:2181",
          "kafka.consumer.id" -> "kafka-consumer")

    val props = new java.utils.Properties()
    kafkaProperties foreach {case (key, value) => props.put(key, value)}

    val tmp_stream = ReceiverLauncher.launch(ssc, props,
      nbReceivers, StorageLevel.MEMORY_ONLY)

    val partitionOffset_stream = ProcessedOffsetManager.getPartitionOffset(tmp_stream, props)

    // Start App
    tmp_stream.foreachRDD(rdd => {
      println("\n\nNumber of records in this batch : " + rdd.count())
    })
    // End App

    // Create DStream
    // val lines = ssc.socketTextStream("localhost", 9092)

    ProcessedOffsetManager.persists(partitionOffset_stream, props)
    ssc.start()
    ssc.awaitTermination()

  }
}


/* OLD CODE 
    println("DDD")

    import spark.implicits._

    // ! Reading from Kafka

    // Read kafka stream ; "drones" topic
    val inputDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("subscribe", "msg")
      .load()

    // Print Struct
    inputDF.printSchema()

    // Output check
    val consoleOutput = inputDF.writeStream
      .outputMode("append")
      .format("console")
      .start()

    // Select Data in Stream
    val dronesMsgDF = inputDF.selectExpr("CAST(value AS STRING)")

    dronesMsgDF.printSchema()

    // ! Convert the Data
    
    // Convert Streamed Data to Drone Msg, using this struct
    val struct = new StructType()
      .add("id", DataTypes.StringType)
      .add("msg_id", DataTypes.StringType)
      .add("timestamp", DataTypes.StringType)

    val dronesMsgStructDF = dronesMsgDF.select(from_json($"value", struct).as("msg"))

    dronesMsgStructDF.printSchema()
    
    // Flatten DF
    val msgsFlatDF = dronesMsgStructDF.selectExpr("msg.id", "msg.msg_id", "msg.timestamp")

    msgsFlatDF.printSchema()

    // TypeConversions (if needed, dates, numbers)
    // val msgDF = msgsFlatDF.withColumn("timestamp", to_timestamp($"timestamp", "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
    
    // ! Processing the DATA


    // ! Output : Result for web, or other

    val resultDF = msgsFlatDF.select(
      concat($"id", lit(" "), $"msg_id").as("key"),
      msgsFlatDF.col("timestamp").cast(DataTypes.StringType).as("timestamp"))


    val kafkaOutput = resultDF.writeStream
      .format("kafka")
      .option("kafka.boostrap.servers", brokers)
      .option("topic", "res")
      .option("checkpointLocation", "./spark/checkpoints")
      .start()

    // Must be after all queries
    // kafkaOutput.awaitTermination()
    // consoleOutput.awaitTermination()

  }
}
*/
