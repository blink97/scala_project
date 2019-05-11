
import java.time.Instant

import MsgClass.{GeoPos, Msg}
import JsonReader.{getJSONbyMapLines, getFileLines}
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

    val id: Int = drone_id

    val root: String = "http://localhost:8080/"

    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    /**
      *
      * @return almost random Msg
      */
    def generateMsg(): Json = {

      val randTempD = 5
      val randTempH = 130
      val randGeo = 100000

      val msg = Msg(id,
        "'Error'", (Random.nextInt(randTempH) + randTempD).toFloat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo), Random.nextInt(randGeo),
          Random.nextInt(randGeo)))

      msg.asJson
    }

    def postJson(json: Json = generateMsg()): Unit = {

      // In order to slow down the process so the server doesn't crash
      Thread.sleep(Random.nextInt(200) + 100)
      
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(POST, uri = root + "msg", entity = ByteString(json.toString())))

      responseFuture
        .onComplete {
          case Success(res) => println(res)
          case Failure(_) => sys.error("something wrong")
        }
    }

    
    def sendNewData(path : String = "testJsonv3.json"): Unit = {
      val res: Iterator[Either[String, Json]] = getJSONbyMapLines(getFileLines(path))

      res.foreach(x => x match {
        case Right(s) => postJson(s)
      })

    }
  }

  def main(args: Array[String]): Unit = {
    val nb_drones = 10
    val nb_messages = 100


    def generateDrones(id: Int = 0): Unit = {

      val drone: Drone = new Drone(id)
      //calls(drone)
      drone.sendNewData()

      if (id <= nb_drones)
        generateDrones(id + 1)
    }

    /*
    def calls(drone: Drone, idMessage: Int = 1): Unit = {

      println("Drone :" + drone.id + " sending message:" + idMessage)

      drone.postJson()

      Thread.sleep(500)

      if (idMessage <= nb_messages)
        calls(drone, idMessage + 1)
    }
    */

    generateDrones()
  }

}