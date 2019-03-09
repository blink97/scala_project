import java.sql._

object PostgresFunctions extends App {
  def initServer(): Connection = {
    // DriverManager.register(new Nothing)
    classOf[org.postgresql.Driver]
    // Make the connection
    DriverManager.getConnection("jdbc:postgresql://localhost:5432/test21", "scala", "42scala")
  }

  def insertDB(conn: Connection, table: String, col: List[String], values: List[String]): Unit = {
    val stt = s"INSERT INTO $table (${col.mkString(",")}) VALUES (${values.mkString(",")})"
    val prepare_statement_add_column = conn.prepareStatement(stt)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
  }

  def lookDBMsg(conn: Connection): Unit = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM msg")
    println("  id   |    msg_id   | drone_id | temp | time | MSG  |")
    while (resultSet.next()) {
      val id = resultSet.getString("id")
      val msg_id = resultSet.getString("msg_id")
      val drone_id = resultSet.getString("drone_id")
      val temp = resultSet.getString("temp")
      val time = resultSet.getString("time")
      val msg = resultSet.getString("msg_type")
      println(id + "  " + msg_id + " " + drone_id + " " + temp + " " + time + " " + msg)
    }
  }

  def lookDBGeoPos(conn: Connection): Unit = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM geopos")
    println("  id   |    msg_id   | x | y | alt | time  |")
    while (resultSet.next()) {
      val id = resultSet.getString("id")
      val msg_id = resultSet.getString("msg_id")
      val x = resultSet.getString("x")
      val y = resultSet.getString("y")
      val alt = resultSet.getString("alt")
      val time = resultSet.getString("time")
      println(id + "  " + msg_id + " " + x + " " + y + " " + alt + " " + time)
    }
  }

  /* TEST */
  val conn = initServer()
  val msgTable = "msg"
  val geoPosTable = "geopos"
  val msgCol = List("ID", "MSG_ID", "DRONE_ID", "MSG_TYPE", "TEMP", "TIME")
  val geoPosCol = List("ID", "MSG_ID", "X", "Y", "ALT", "TIME")

  insertDB(conn, msgTable, msgCol, List("1", "1", "1", "'START'", "23.0", "0"))
  insertDB(conn, msgTable, msgCol, List("2", "1", "2", "'START'", "23.4", "0"))
  insertDB(conn, msgTable, msgCol, List("3", "1", "3", "'START'", "25.2", "0"))
  insertDB(conn, msgTable, msgCol, List("4", "1", "4", "'START'", "23.4", "0"))
  insertDB(conn, msgTable, msgCol, List("5", "1", "5", "'START'", "23.2", "0"))
  insertDB(conn, msgTable, msgCol, List("6", "1", "6", "'START'", "23.4", "0"))

  /*
  val prepare_statement = conn.prepareStatement(s" Insert into lol Values (2),(3),(5)")
  prepare_statement.executeUpdate()
  prepare_statement.close()

  try {
    val stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

    val rs = stm.executeQuery("SELECT * FROM lol")

    while(rs.next) {
      println(rs.getString("id"))
    }
  } finally {
    conn.close()
  }
  */

  lookDBGeoPos(conn)
  lookDBMsg(conn)
}
