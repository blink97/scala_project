
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

  def main(args: Array[String]): Unit = {
    
    // TODO: Error message if args(0) is empty

    val nb_messages = 100
    val id = args(0).toInt

    generateData()
    
    def generateData(idMessage: Int = 1): Unit = {
      val json : Json = MsgFactory(id).asJson

      val fw = new FileWriter("json_Drone" + args(0) + ".json", true)
      try {
      fw.write(json.toString + "\n")
      }
      finally fw.close() 

      if (idMessage <= nb_messages)
        generateData(idMessage + 1)
    }
  }

}