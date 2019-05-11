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
    val msgCol = List("MSG_ID", "DRONE_ID", "MSG_TYPE", "TEMP", "TIME")
    val geoPosCol = List("MSG_ID", "X", "Y", "ALT", "TIME")

    insertMsg(conn, new Msg(78, "'Error'", 23.6f, "'2007-02-02 00:00:00.00'", GeoPos(34243, 23224, 232)))
    insertMsg(conn, new Msg(6, "'START'", 16.5f, "'2019-04-11 14:37:56.498443'", GeoPos(32222, 324, 100)))
    insertMsg(conn, new Msg(1, "'START'", 23.6f, "'2007-01-01 00:00:00.00'", GeoPos(34243, 3224, 232)))
    insertMsg(conn, new Msg(2, "'START'", 23.6f, "'2008-01-01 00:00:00.00'", GeoPos(363, 324, 232)))
    insertMsg(conn, new Msg(3, "'START'", 23.6f, "'2009-01-01 00:00:00.00'", GeoPos(73, 624, 232)))
    insertMsg(conn, new Msg(6, "'START'", 16.5f, "'2019-04-11 14:37:56.498443'", GeoPos(32222, 324, 100)))
    insertMsg(conn, MsgClass.MsgFactory)

    // lookDBGeoPos(conn)
    lookDBMsg(conn)
    println(getDBMsg(conn))

    conn.close()
  }

  run(new Test)
}
