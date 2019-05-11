
import java.time.Instant

import MsgClass.{GeoPos, Msg, MsgFactory}
import JsonReader.{getJSONbyMapLines, getFileLines}
import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpMethods._
import akka.stream.ActorMaterializer
import argonaut._
import Argonaut._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.util.ByteString

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Random, Success}

import java.sql.Timestamp
import java.io.FileWriter

object DroneDataGenerator {

  /**
    * IMPERATIVE SCRIPT WARNING !!!!
    * Only used to generate ONCE (AND ONLY ONE TIME) data json for drones
    * @param args nb of drones
    */
  def main(args: Array[String]): Unit = {
    val nb_messages = 100
    val ids = args(0).toInt
    var fws: Array[FileWriter] = new Array[FileWriter](ids + 1)
    for (i <- 1 to ids) {
      fws(i) = new FileWriter("db/drones-json-data/json_Drone" + i + ".json", true)
    }

    for (_ <- 1 to nb_messages) {
      Thread.sleep(Random.nextInt(1000) + 1000)
      for (i <- 1 to ids) {
        val json: Json = MsgFactory(i).asJson
        try {
          fws(i).write(json.toString + "\n")
        }
      }
    }

    for (i <- 1 to ids) {
      fws(i).close()
    }

    // TODO: Error message if args(0) is empty

    /*
    val nb_messages = 100
    val ids = args(0).toInt

    val fw = new FileWriter("db/drones-json-data/json_Drone" + args(0) + ".json", true)


    generateData()
    
    def generateData(idMessage: Int = 1): Unit = {
      Thread.sleep(Random.nextInt(1000) + 1000)
      val json : Json = MsgFactory(id).asJson

      try {
        fw.write(json.toString + "\n")
      }

      if (idMessage <= nb_messages)
        generateData(idMessage + 1)
    }
    fw.close()
    */
  }

}