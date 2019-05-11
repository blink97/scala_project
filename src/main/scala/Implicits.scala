import java.sql._

import argonaut._
import Argonaut._
import MsgClass.{GeoPos, Msg}
import java.sql.ResultSet

/**
  * Class used to correctly iterate over a Stream
  * (Used in collection of the reponse of the database)
  */
object Implicits {

  implicit class ResultSetStream(resultSet: ResultSet) {

    def toStream: Stream[ResultSet] = {
      new Iterator[ResultSet] {
        def hasNext = resultSet.next()

        def next() = resultSet
      }.toStream
    }
  }

}