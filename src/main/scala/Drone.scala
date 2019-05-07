
import java.time.Instant

import MsgClass.{GeoPos, Msg}
import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpMethods._
import akka.stream.ActorMaterializer
import argonaut._
import Argonaut._
import WebClient.root
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.util.ByteString

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Random, Success}

import java.sql.Timestamp

object Client {

  class Drone(drone_id: Int) {

    val id : Int = drone_id
    var message_id : Int = 0 // TODO : var

    val root : String = "http://localhost:8080/"

    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    /**
      *
      * @return almost random Msg
      */
    def generateMsg() : Json = {

      val randTempD = 5
      val randTempH = 130
      val randGeo = 100000

      val msg = Msg(id, message_id,
        "'Error'", (Random.nextInt(randTempH) + randTempD).toFloat, "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo), Random.nextInt(randGeo), Random.nextInt(randGeo)))  

      message_id += 1 // TODO : var

      msg.asJson
    }

    def postJson(json : Json = generateMsg()) : Unit = {

      //val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(POST, uri = root + "msg/" + id, entity = ByteString(json.toString())))
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(POST, uri = root + "msg", entity = ByteString(json.toString())))

      responseFuture
        .onComplete {
          case Success(res) => println(res)
          case Failure(_)   => sys.error("something wrong")
        }
    }
  }

  def main(args: Array[String]): Unit = {
    val nb_drones = 10
    val nb_messages = 100


    def generateDrones(id : Int = 0) : Unit = {

      val drone : Drone = new Drone(id)
      calls(drone)

      if (id <= nb_drones)
        generateDrones(id + 1)
    }


    def calls(drone: Drone, idMessage : Int = 1) : Unit = {

      println("Drone :" + drone.id + " sending message:" + idMessage)

      drone.postJson()

      if (idMessage <= nb_messages)
        calls(drone, idMessage + 1)
    }

    generateDrones()
  }
  
}