import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{StatusCodes, _}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import argonaut._
import Argonaut._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object WebServer{

  /**
    * WebServer function
    * Enable endpoints to get frontend visualisation
    * Enable endpoints interactions with the database
    * @param args None required
    */
  def main(args: Array[String]) {
    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher


    lazy val route: Route =
      path("") {
        get {
          (pathEndOrSingleSlash & redirectToTrailingSlashIfMissing(StatusCodes.TemporaryRedirect)) {
            println("Got dashboard")
            getFromFile("dashboard.html")
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
                  getFromFile("../stream/part-00000-6215232b-8e3b-488d-acb0-917e0377e68a-c000.json")
                }/*,
                post {
                  entity(as[String]) { s =>
                    val msgJson = Parse.parse(s)
                    msgJson match {
                      case Right(value) =>
                        complete("OK " + value.toString + " !")
                      case Left(value) => complete("Error " + value)
                    }
                  }
                }*/
              )
            }
          )
        }/*,
            /* Remove all msg from this drone id */
            path("removedronemsg" / Segment) { id =>
              concat(
                get {
                  /* TODO patch a row of the database */
                  val idint = id.toInt.toString
                  PostgresFunctions.deleteDBQuery(conn, s"DELETE FROM msg WHERE drone_id = $idint;")
                  complete(
                    HttpEntity(ContentTypes.`text/html(UTF-8)`,
                      "Done!")
                  )
                },
                delete {
                  PostgresFunctions.deleteDBQuery(conn, s"DELETE FROM msg WHERE drone_id = $id;")
                  complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
                    "Done!"))
                }
              )
            } ~
              /* Give all messages from this drone id */
              path("drone" / Segment) { id =>
                /* curl -i -X GET "http://localhost:8080/msg/drone/1"   == Donne tout les msg du drone 1*/
                get {
                  val resultSet = PostgresFunctions.anyDBQuery(conn, s"SELECT * FROM msg JOIN geopos ON msg.msg_id = geopos.msg_id WHERE msg.drone_id = $id;")
                  complete(
                    HttpEntity(ContentTypes.`application/json`,
                      resultSet.toStream.map(rs => Msg(rs.getInt("drone_id"),
                        rs.getString("msg_type"),
                        rs.getFloat("temp"),
                        rs.getString("time"),
                        GeoPos(rs.getInt("x"), rs.getInt("y"), rs.getInt("alt"))).asJson)
                        .mkString("\n")
                    )
                  )
                }
              }
          )
        }

*/
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}
