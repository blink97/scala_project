import java.sql._

object PostgresFunctions extends App {
  def initServer(): Connection = {
    // DriverManager.register(new Nothing)
    classOf[org.postgresql.Driver]
    // Make the connection
    DriverManager.getConnection("jdbc:postgresql://localhost:5432/test42", "scala", "42scala")
  }

  def insertDB(conn: Connection, table: String, col: List[String], values: List[String]): Unit = {
    println(s"INSERT INTO $table ($col) VALUES ($values)")
    val e = "INSERT INTO k (ID, DRONE_ID, MSG_TYPE) VALUES (4, 2, RESD)"
    // val prepare_statement_add_column = conn.prepareStatement(s"INSERT INTO $table ($col) VALUES ($values) ")
    val prepare_statement_add_column = conn.prepareStatement(e)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
  }

  def lookDB(conn: Connection, table: String): Unit = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM $table")
    while (resultSet.next()) {
      val type_column = resultSet.getString("type")
      val color_column = resultSet.getString("color")
      val install_date_column = resultSet.getString("install_date")
      println("type, color, install_date = " + type_column + ", " + color_column + ", " + install_date_column)
    }
  }

  val conn = initServer()

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

  /*
  insertDB(conn, "MSG", List("ID", "DRONE_ID", "MSG_TYPE"), List("4", "2", "RESD"))
  lookDB(conn, "MSG")
*/
}
