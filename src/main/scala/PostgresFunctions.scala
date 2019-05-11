import java.sql._
import argonaut._
import Argonaut._
import MsgClass.{GeoPos, Msg}
import java.sql.ResultSet
import Implicits._

/**
  * PostgresFunctions contain all usable fonctions for the PostgreSQL database
  */
object PostgresFunctions extends App {

  /**
    * Connect to the Database
    * @return a Connection
    */
  def initServer(): Connection = {
    // DriverManager.register(new Nothing)
    classOf[org.postgresql.Driver]
    // Make the connection : use the user scala and password 42scala
    DriverManager.getConnection("jdbc:postgresql://localhost:5432/scalaproject", "scala", "42scala")
  }

  /**
    * Insert a Msg in the database, will add a row in msg table
    * And update drone table
    * @param conn The Database Connection Object
    * @param msg The Msg Object
    */
  def insertMsg(conn: Connection, msg: Msg): Unit = {
    val msgCol = List("DRONE_ID", "MSG_TYPE", "TEMP", "TIME", "X", "Y", "ALT")
    val args = List(msg.droneId, msg.msgType, msg.temp, msg.time, msg.geoPos.x, msg.geoPos.y, msg.geoPos.alt)
      .map(x => x.toString)
    val stt = s"INSERT INTO msg (${msgCol.mkString(",")}) VALUES (${args.mkString(",")})"
    val prepare_statement_add_column = conn.prepareStatement(stt)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
  }

  /*
  def insertGeoPos(conn: Connection, geo: GeoPos, id: Int, time: String): Unit = {
    val geoPosCol = List("X", "Y", "ALT", "TIME")
    val args = List(geo.x, geo.y, geo.alt, time).map(x => x.toString)
    val stt = s"INSERT INTO geopos (${geoPosCol.mkString(",")}) VALUES (${args.mkString(",")})"
    val prepare_statement_add_column = conn.prepareStatement(stt)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
  }
  */

  /**
    * General insertion in Database
    * @param conn the Database Connection Object
    * @param table the Table in for insertion
    * @param col Columns list for the query building
    * @param values Values for each Columns in the query
    */
  def insertDB(conn: Connection, table: String, col: List[String], values: List[String]): Unit = {
    val stt = s"INSERT INTO $table (${col.mkString(",")}) VALUES (${values.mkString(",")})"
    val prepare_statement_add_column = conn.prepareStatement(stt)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
  }

  /**
    * Execute a Query
    *
    * @param conn the Database Connection Object
    * @param query SQL query, watch out for sql injections
    * @return
    */
  def deleteDBQuery(conn: Connection, query: String) = {
    val statement = conn.createStatement()
    statement.executeQuery(query)
  }

  /**
    * Execute a Query
    *
    * @param conn the Database Connection Object
    * @param query SQL query, watch out for sql injections
    * @return ResultSet Streamed
    */
  def anyDBQuery(conn: Connection, query: String): Stream[ResultSet] = {
    val statement = conn.createStatement()
    // println(query)
    val resultSet = statement.executeQuery(query)
    resultSet.toStream
  }

  /**
    * Get Msg row from database (DEBUG use)
    * @param conn the database connection
    * @return
    */
  def getDBMsg(conn: Connection): String = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM msg")
    resultSet.toStream.map(rs => rs.getString("id") + " "
      + rs.getString("drone_id") + " "
      + rs.getString("temp") + " " + rs.getString("time") + " " +
      rs.getString("msg_type") + "\n").mkString("")
  }

  def getJsonAsText(s: List[JsonField]): String = {
    println(s)
    s.foreach(x => println(x))
    s.mkString("\n")
  }

  /**
    * Get all msg rows from msg table,
    * Convert them to Msg Object,
    * Convert them to JSON argonaut,
    * Convert it to a big string with all the jsons concatenated
    * @param conn the database Connection Object
    * @return string of Jsons of Msg Objects
    */
  def getDBMsgJson(conn: Connection): String = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM msg;")
    Iterator.continually {
      resultSet
    }.takeWhile {
      _.next()
    }.map { rs =>
      Msg(rs.getInt("drone_id"),
        rs.getString("msg_type"),
        rs.getFloat("temp"),
        rs.getString("time"),
        GeoPos(rs.getInt("x"), rs.getInt("y"), rs.getInt("alt")))
    }
      .toList
      .map(x => x.asJson)
      .map(x => x.toString())
      .mkString("\n")
  }

  /**
    * Get Msg rows from database (DEBUG use)
    * @param conn the database connection
    * @return
    */
  def lookDBMsg(conn: Connection): Unit = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM msg")
    println("id msg drone temp time MSG")
    println(resultSet.toStream.map(rs => rs.getString("id") + " "
      + rs.getString("drone_id") + " "
      + rs.getString("temp") + " " + rs.getString("time") + " "
      + rs.getString("x") + " "
      + rs.getString("y") + " " + rs.getString("alt") +
      rs.getString("msg_type") + "\n").mkString(""))

  }

}
