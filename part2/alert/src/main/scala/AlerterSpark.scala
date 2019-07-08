import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.util.Random
import java.time.Instant
import java.sql.Timestamp

import java.time.Instant

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Random, Success}


import java.time.{LocalDate, Period}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark._
import org.apache.spark.SparkContext
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

import org.apache.spark.rdd._

import java.io.File


object SparkConsumor {
  def main(args: Array[String]): Unit = {
    new SparkConsumor("localhost:9092").process()
  }
}

class SparkConsumor(brokers: String) {
 
  case class DataMsg(
    msg: String,
    value: String
  )

"com.typesafe.play" %% "play-json" % "2.4.0"

resolver += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"


  /**
    * Drone Geographical Position Class/Tuple
    *
    * @param x   Int X Coord
    * @param y   Int Y Coord
    * @param alt Int Altitude in meters
    */
  case class GeoPos(x: Int, y: Int, alt: Int)

  /**
    * Drone Message Class
    *
    * @param droneId Int drone Id
    * @param msgId   Int message id
    * @param msgType String the message content
    * @param temp    Float Temperature
    * @param time    Long TimeStamp
    * @param geoPos  Object Geographical Position Object
    */
  case class Msg(
                  droneId: Int,
                  msgType: String,
                  temp: Float,
                  time: String,
                  // battery: Int
                  geoPos: GeoPos
                )



  implicit val tweetFormat = Json.format[Tweet]
    
  def StringToTweet(str: String) = Json
    .parse(str)
    .validate[Tweet] match {
      case JsError(e) => println(e); None
      case JsSuccess(t, _) => Some(t)
    }

  def getListOfFiles(dir: String) : List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def process(): Unit = {

    val conf = new SparkConf()
      .setAppName("sparkAlert")
      .setMaster("local[*]")


    val sc = SparkContext.getOrCreate(conf)


    val files = getListOfFiles("../stream")

    val alertRDD = sc.textFile("../steam/*.json")

    val alertJsonRDD = alertRDD.map(s => Parse.parse(s).right)
    
    val countOK = alertJsonRDD.filter(js => js.msgType == "OK").count()

    val countERROR = alertJsonRDD.filter(js => js.msgType == "ERROR").count()

    val countHOT = alertJsonRDD.filter(js => js.msgType == "HOT").count()

    val countCOLD = alertJsonRDD.filter(js => js.msgType == "COLD").count()

    val countHOT = alertJsonRDD.filter(js => js.msgType == "HOT").count()

    val countCOLD = alertJsonRDD.filter(js => js.msgType == "COLD").count()

    println("OK : " + countOK + "   ERROR " + countERROR +  "   \n")

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
