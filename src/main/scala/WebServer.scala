import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn


object WebServer {

  def main(args: Array[String]) {

    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val route =
      path("") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>GET Root</h1>"))
        }
      } ~
        path("json") {
          get {
            complete(HttpEntity(ContentTypes.`application/json`, JsonReader
              .getJSONbyMapLines(JsonReader.getFileLines("testJsonV2.json"))
              .collect { case Right(value) => value }.next().toString())))
          }
        } ~
        path("json" / Segment) { id =>
          get {
            complete(if (id.toInt >= 0 && id.toInt < 5) {
              HttpEntity(ContentTypes.`application/json`, JsonReader
                .getJSONbyMapLines(JsonReader
                  .getFileLines("db/" + id + ".json")).collect { case Right(value) => value }.next().toString())
            }
            else {
              HttpEntity(ContentTypes.`text/html(UTF-8)`, "Error JSON requested don't exist")
            })
          }
        } ~
        path("hello") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          }
        }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }


}