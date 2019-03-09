import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{StatusCodes, _}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import argonaut._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn


object WebServer {

  def main(args: Array[String]) {

    val conn = PostgresFunctions.initServer()
    val idMin = 0
    val idMax = 1000

    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher


    lazy val route: Route =
      path("") {
        get {
          (pathEndOrSingleSlash & redirectToTrailingSlashIfMissing(StatusCodes.TemporaryRedirect)) {
            println("Got dashboard")
            getFromFile("src/main/resources/web/dashboard.html")
          }
          /*~ {
            println("Got index.html")
            getFromDirectory("src/main/resources/web")
          }*/
        }
      } ~
      pathPrefix("msg") {
        concat(
          pathEnd {
            concat(
              get {
                /* All msg as JSON */
                complete(HttpEntity(ContentTypes.`application/json`, PostgresFunctions.getDBMsg(conn)))
              },
              post {
                entity(as[String]) { s =>
                  val msgJson = Parse.parse(s)
                  msgJson match {
                    case Right(value) => /* Send msg as MSg to DataBase */ complete("OK " + value.toString + " !")
                    case Left(value) => complete("Error " + value)
                  }
                }
              }
            )
          },
          path(Segment) { id =>
            concat(
              get {
                complete(if (id.toInt >= idMin && id.toInt < 5) {
                  HttpEntity(ContentTypes.`application/json`, JsonReader
                    .getJSONbyMapLines(JsonReader.getFileLines("db/" + id + ".json"))
                    .collect { case Right(value) => value }.next().toString())
                } else {
                  HttpEntity(ContentTypes.`text/html(UTF-8)`,
                    "Error JSON requested don't exist")
                })
              },
              delete {
                val X = "REmove json or line in json"
                complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
                  "WORK"))
              }
            )
          }
        )
      }


    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}