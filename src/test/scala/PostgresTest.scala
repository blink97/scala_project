import java.sql.Connection

import MsgClass.{GeoPos, Msg}
import PostgresFunctions._
import org.scalatest.{FunSuite, run}

object PostgresTest extends App {
  class Test extends FunSuite {
    /* TEST */
    val conn: Connection = initServer()
    test("Connection exist") {
      assert(conn != null)
    }
    val msgTable = "msg"
    val geoPosTable = "geopos"
    val msgCol = List("MSG_ID", "DRONE_ID", "MSG_TYPE", "TEMP", "TIME")
    val geoPosCol = List("MSG_ID", "X", "Y", "ALT", "TIME")

    insertDB(conn, msgTable, msgCol, List("1", "1", "'START'", "23.0", "0"))
    insertDB(conn, msgTable, msgCol, List("1", "2", "'START'", "23.4", "0"))
    insertDB(conn, msgTable, msgCol, List("1", "3", "'START'", "25.2", "0"))
    insertDB(conn, msgTable, msgCol, List("4", "1", "'START'", "23.4", "0"))
    insertDB(conn, msgTable, msgCol, List("5", "1", "'START'", "23.2", "0"))
    insertDB(conn, msgTable, msgCol, List("6", "1", "'START'", "23.4", "0"))

    insertMsg(conn, new Msg(78, 34, "'Error'", 23.6f, 646465L, GeoPos(34243, 23224, 232)))

    lookDBGeoPos(conn)
    lookDBMsg(conn)

    println(getDBMsg(conn))

    conn.close()
  }

  run(new Test)
}
