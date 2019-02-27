import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import HttpMethods._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}



object WebClient {

  val root : String = "http://localhost:8080/"

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {

    requestAllJson()
    requestJson("3")
    postJson("3")
    deleteJson("3")

  }

  def requestAllJson(id : String = "") : Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(GET, uri = root + "json" + id))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_)   => sys.error("something wrong")
      }
  }

  def requestJson(id : String) : Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(GET, uri = root + "json/" + id))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_)   => sys.error("something wrong")
      }
  }

  def postJson(id : String) : Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(POST, uri = root + "json/" + id))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_)   => sys.error("something wrong")
      }
  }

  def putJson(id : String) : Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(PUT, uri = root + "json/" + id))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_)   => sys.error("something wrong")
      }
  }

  def deleteJson(id : String) : Unit = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(DELETE, uri = root + "json/" + id))

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_)   => sys.error("something wrong")
      }
  }
}
