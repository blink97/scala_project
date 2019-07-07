import java.time.{LocalDate, Period}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DataTypes, StructType}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies._
import kafka.serializer.StringDecoder
import kafka.serializer.DefaultDecoder
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.clients.consumer.ConsumerRecord

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

    val ssc = new StreamingContext(conf, Seconds(5))


    val topic = "msg"
    val topics = Array("msg")
    val topicSet = topic.toSet()

    val zkhost = "localhost"
    val zkports = "2181"

    val nbReceivers = 1

    /*
    val kafkaProperties: Map[String, String] = 
      Map("zookeeper.hosts" -> zkhost,
          "zookeeper.port" -> zkports,
          "kafka.topic" -> topic,
          "zookeeper.consumer.connection" -> "localhost:2181",
          "kafka.consumer.id" -> "kafka-consumer")

    val props = new java.util.Properties()
    kafkaProperties foreach {case (key, value) => props.put(key, value)}
    */
   val kafkaParams = 
     Map[String, Object](
  "bootstrap.servers" -> "localhost:9092,anotherhost:9092",
  "key.deserializer" -> classOf[StringDeserializer],
  "value.deserializer" -> classOf[StringDeserializer],
  "group.id" -> "use_a_separate_group_id_for_each_stream",
  "auto.offset.reset" -> "latest",
  "enable.auto.commit" -> (false: java.lang.Boolean)
  )



   val directKafkaStream = KafkaUtils
     .createDirectStream[String, String](
       ssc, PreferConsistent, Subscribe[String, String](topics, kafkaParams)
     )


   /*
    val tmp_stream = ReceiverLauncher.launch(ssc, props,
      nbReceivers, StorageLevel.MEMORY_ONLY)

    val partitionOffset_stream = ProcessedOffsetManager.getPartitionOffset(tmp_stream, props)
   */

    // Start App
    directKafkaStream.foreachRDD(rdd => {
      println("\n\nNumber of records in this batch : " + rdd.count())
    })
    directKafkaStream.map(record => println("\n\n " + record.key + " : " + record.value + "\n\n"))
    // End App

    // Create DStream
    // val lines = ssc.socketTextStream("localhost", 9092)

    // ProcessedOffsetManager.persists(partitionOffset_stream, props)
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
