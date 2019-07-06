import spark.implicits._

class SparkConsumor(brokers: String) {
  def process(): Unit = {
    // Init SparkSession
    val spark = SparkSession.builder()
      .appName("spark")
      .master("local[*]")
      .getOrCreate()

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

    consoleOutput.awaitTermination()

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


    // !

  }
}

