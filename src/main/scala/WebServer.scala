import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.StatusCodes

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn
import java.io.File
import java.nio.file.Paths

import MsgClass.{GeoPos, Msg}
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.FileIO
import argonaut._
import Argonaut._


import scala.util.{Failure, Success}

object WebServer {

  def main(args: Array[String]) {

    val idMin = 0
    val idMax = 1000

    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    /*
    entity(as[User]) { user =>
                  val userCreated: Future[ActionPerformed] =
                    (userRegistryActor ? CreateUser(user)).mapTo[ActionPerformed]
                  onSuccess(userCreated) { performed =>
                    log.info("Created user [{}]: {}", user.name, performed.description)
                    complete((StatusCodes.Created, performed))
                  }
                }

     */

    lazy val routeDB: Route =
      pathPrefix("msg") {
        concat(
          pathEnd {
            concat(
              get {
                /* All msg as JSON */
                complete(HttpEntity(ContentTypes.`application/json`, ""))
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

    /* Warning Need Tests as Lazy */
    lazy val routeDL: Route =
      pathPrefix("json") {
        concat(
          pathEnd {
            concat(
              get {
                complete(HttpEntity(ContentTypes.`application/json`, JsonReader
                  .getJSONbyMapLines(JsonReader.getFileLines("testJsonV2.json"))
                  .collect { case Right(value) => value }.next().toString()))
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


    lazy val routeStaticRes =
      get {
        (pathEndOrSingleSlash & redirectToTrailingSlashIfMissing(StatusCodes.TemporaryRedirect)) {
          println("RES")

          getFromFile("src/main/resources/web/dashboard.html")
        } ~ {
          println("RES")

          getFromDirectory("src/main/resources/web")
        }
      }

    val route =
      path("") {
        //get {
        //  complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>GET Root</h1>"))
        //}
        println("RES")
        getFromResource("src/main/resources/web/dashboard.html")
      } ~
        path("json") {
          get {
            complete(HttpEntity(ContentTypes.`application/json`, JsonReader
              .getJSONbyMapLines(JsonReader.getFileLines("testJsonV2.json"))
              .collect { case Right(value) => value }.next().toString()))
          }
        } ~
        path("json/delete" / Segment) { id =>
          delete {
            /* val fileTemp = new File("db/" + id + ".json")
            if (fileTemp.exists) {
              fileTemp.delete()
            } */
            /*
            complete(if (id.toInt => idMin && id.toInt < idMax
            )
            {
              HttpEntity(ContentType.`text/html(UTF-8)`,
                "File deleted")
            }
            else
            {
              HttpEntity(ContentTypes.`text/html(UTF-8)`,
                "Error JSON requested delete don't exist")
            })
            */
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
              "Error JSON " + id + "  requested delete don't exist"))
          }
        } ~
        path("json/patch" / Segment) { id =>
          patch {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
              "Error JSON " + id + "  requested to delete don't exist"))
          }
        } ~
        path("json/upload") {
          post {
            fileUpload("JsonUpload") {
              case (fileInfo, fileStream) =>
                val sink = FileIO.toPath(Paths.get("db/") resolve fileInfo.fileName)
                val writeResult = fileStream.runWith(sink)
                onSuccess(writeResult) { result =>
                  result.status match {
                    case Success(_) => complete(s"Successfully written ${result.count} bytes")
                    case Failure(e) => throw e
                  }
                }
            }
          }
        } ~
        path("json/post" / Segment) { id =>
          post {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
              "Error JSON requested don't exist"))
            /*
            val upload: Future[Boolean] = true

            upload onComplete {
              case Success(_) => val resS = "JSON " + id + " has been posted"
              case Failure(_) => val resS = "Error Json"
            }
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
              resS))
              */
          }
        } ~
        path("json" / Segment) { id =>
          get {
            complete(if (id.toInt >= idMin && id.toInt < 5) {
              HttpEntity(ContentTypes.`application/json`, JsonReader
                .getJSONbyMapLines(JsonReader.getFileLines("db/" + id + ".json"))
                .collect { case Right(value) => value }.next().toString())
            }
            else {
              HttpEntity(ContentTypes.`text/html(UTF-8)`,
                "Error JSON requested don't exist")
            })
          }
        }

    val bindingFuture = Http().bindAndHandle(routeStaticRes, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}