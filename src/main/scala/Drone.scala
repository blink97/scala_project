
import java.time.Instant

import MsgClass.{GeoPos, Msg}
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

object Client {

  class Drone(drone_id: Int) {

    val id: Int = drone_id

    val root: String = "http://localhost:8080/"

    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    /**
    * postJson
    *
    * @param json - json to send to the server
    * Send the json to the server
    */
    def postJson(json: Json): Unit = {

      // In order to slow down the process so the server doesn't crash
      Thread.sleep(Random.nextInt(200) + 100)

      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(POST, uri = root + "msg", entity = ByteString(json.toString())))

      responseFuture
        .onComplete {
          case Success(res) => println(res)
          case Failure(_) => sys.error("something wrong")
        }
    }

    /**
    * sendNewData
    *
    * @param path - path to the json containning data
    * Call postJson on every Json in the file
    */
    def sendNewData(path : String = "testJsonv3.json"): Unit = {
      val res: Iterator[Either[String, Json]] = getJSONbyMapLines(getFileLines(path))

      res.foreach(x => x match {
        case Right(s) => postJson(s)
      })

    }
  }

  def main(args: Array[String]): Unit = {
    
    // TODO: Error message if args(0) is empty

    val drone: Drone = new Drone(args(0).toInt)

    drone.sendNewData("db/drones-json-data/json_Drone" + args(0) + ".json")

  }

}