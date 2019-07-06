import java.time.{LocalDate, Period}

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DataTypes, StructType}

class SparkConsumor(brokers: String) {
  def process(): Unit = {
    // Init SparkSession
    val spark = SparkSession.builder()
      .appName("spark")
      .master("local[*]")
      .getOrCreate()


    import spark.implicits._

    // ! Reading from Kafka

    // Read kafka stream ; "drones" topic
    val inputDF = spark.readStream
      .format("kafka")
      .option("kafka.boostrap.servers", brokers)
      .option("subscribe", "drones")
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

    // ! Convert the Data
    
    // Convert Streamed Data to Drone Msg, using this struct
    val struct = new StructType()
      .add("id", DataTypes.StringType)
      .add("msg_id", DataTypes.StringType)
      .add("timestamp", DataTypes.StringType)

    val dronesMsgStructDF = dronesMsgDF.select(from_json($"value", struct).as("msg"))

    dronesMsgDF.printSchema()


    // Flatten DF
    val msgsFlatDF = dronesMsgDF.selectExpr("msg.id", "msg.msg_id", "msg.timestamp")

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
    kafkaOutput.awaitTermination()
    consoleOutput.awaitTermination()

  }
}

