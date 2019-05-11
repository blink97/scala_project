import java.sql.Connection
import java.time.Instant
import java.sql.Timestamp
import MsgClass.{GeoPos, Msg}
import PostgresFunctions._
import org.scalatest.{FunSuite, run}

object PostgresTest extends App {

  class Test extends FunSuite {
    /* TEST */

    val x = Instant.now
    println(x.toString)
    println(Timestamp.from(x))

    val conn: Connection = initServer()
    test("Connection exist") {
      assert(conn != null)
    }
    val msgTable = "msg"
    val geoPosTable = "geopos"
    val msgCol = List("DRONE_ID", "MSG_TYPE", "TEMP", "TIME")
    val geoPosCol = List("X", "Y", "ALT", "TIME")

    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

     insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

     insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)
    insertMsg(conn, MsgClass.MsgFactory)

    // lookDBGeoPos(conn)
    lookDBMsg(conn)
    println(getDBMsg(conn))

    conn.close()
  }

  run(new Test)
}
