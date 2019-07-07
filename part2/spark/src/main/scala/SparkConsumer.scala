import java.time.{LocalDate, Period}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DataTypes, StructType}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies._
import kafka.serializer.StringDecoder
import kafka.serializer.DefaultDecoder
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.sql.SQLContext


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

    val ssc = new StreamingContext(conf, Seconds(25))


    val topic = "msg"
    val topics = Array("msg")
    val topicsSet = topic.split(",").toSet
    val zkhost = "localhost"
    val zkports = "2181"

    val nbReceivers = 1

    val kafkaParams = 
      Map[String, Object](
        "bootstrap.servers" -> "localhost:9092",
        "key.deserializer" -> classOf[StringDeserializer],
        "value.deserializer" -> classOf[StringDeserializer],
        "group.id" -> "1",
        )

    val directKafkaStream = KafkaUtils
      .createDirectStream[String, String](
        ssc,
        PreferConsistent,
        Subscribe[String, String](topicsSet, kafkaParams)
      )

    // Start App
    directKafkaStream.foreachRDD(rdd => {
      println("\n\nNumber of records in this batch : " + rdd.count())
      if (!rdd.isEmpty()) {
        val sqlContext = SQLContextSingleton.getInstance(rdd.sparkContext)
        import sqlContext.implicits._

        val test = rdd.map(record => Rec(record.value)).toDF()
        test.write.mode(SaveMode.Append).json("../stream")
        test.show()
      }
    })
    // End App

    ssc.start()
    ssc.awaitTermination()

  }
}


case class Rec(msg: String)

object SQLContextSingleton {
  @transient private var instance: SQLContext = _

  def getInstance(sparkContext: SparkContext) : SQLContext = {
    if (instance == null) {
      instance = new SQLContext(sparkContext)
    }
    instance
  }
}

