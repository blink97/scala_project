import java.sql._
import argonaut._
import Argonaut._
import MsgClass.{GeoPos, Msg}
import java.sql.ResultSet
import Implicits._

object PostgresFunctions extends App {
  val msgTable = "msg"
  val geoPosTable = "geopos"
  val msgCol = List("MSG_ID", "DRONE_ID", "MSG_TYPE", "TEMP", "TIME")
  val geoPosCol = List("MSG_ID", "X", "Y", "ALT", "TIME")

  def initServer(): Connection = {
    // DriverManager.register(new Nothing)
    classOf[org.postgresql.Driver]
    // Make the connection
    DriverManager.getConnection("jdbc:postgresql://localhost:5432/scalaproject", "scala", "42scala")
  }

  def insertMsg(conn: Connection, msg: Msg): Unit = {
    val msgCol = List("MSG_ID", "DRONE_ID", "MSG_TYPE", "TEMP", "TIME")
    val args = List(msg.msgId, msg.droneId, msg.msgType, msg.temp, msg.time)
      .map(x => x.toString)
    val stt = s"INSERT INTO msg (${msgCol.mkString(",")}) VALUES (${args.mkString(",")})"
    val prepare_statement_add_column = conn.prepareStatement(stt)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
    insertGeoPos(conn, msg.geoPos, msg.msgId, msg.time)
  }

  def insertGeoPos(conn: Connection, geo: GeoPos, id: Int, time: String): Unit = {
    val geoPosCol = List("MSG_ID", "X", "Y", "ALT", "TIME")
    val args = List(id, geo.x, geo.y, geo.alt, time).map(x => x.toString)
    val stt = s"INSERT INTO geopos (${geoPosCol.mkString(",")}) VALUES (${args.mkString(",")})"
    val prepare_statement_add_column = conn.prepareStatement(stt)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
  }

  def insertDB(conn: Connection, table: String, col: List[String], values: List[String]): Unit = {
    val stt = s"INSERT INTO $table (${col.mkString(",")}) VALUES (${values.mkString(",")})"
    val prepare_statement_add_column = conn.prepareStatement(stt)
    prepare_statement_add_column.executeUpdate()
    prepare_statement_add_column.close()
  }

  def deleteDBQuery(conn: Connection, query: String) = {
    val statement = conn.createStatement()
    statement.executeQuery(query)
  }

  def anyDBQuery(conn: Connection, query: String): Stream[ResultSet] = {
    val statement = conn.createStatement()
    // println(query)
    val resultSet = statement.executeQuery(query)
    resultSet.toStream
  }

  def getDBMsg(conn: Connection): String = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM msg")
    resultSet.toStream.map(rs => rs.getString("id") + " "
      + rs.getString("msg_id") + " " + rs.getString("drone_id") + " "
      + rs.getString("temp") + " " + rs.getString("time") + " " +
      rs.getString("msg_type") + "\n").mkString("")
  }

  def getJsonAsText(s: List[JsonField]): String = {
    println(s)
    s.foreach(x => println(x))
    s.mkString("\n")
  }

  def getDBMsgJson(conn: Connection): String = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM msg JOIN geopos ON msg.msg_id = geopos.msg_id;")
    Iterator.continually {
      resultSet
    }.takeWhile {
      _.next()
    }.map { rs =>
      Msg(rs.getInt("drone_id"),
        rs.getInt("msg_id"),
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

  def getDBGeoPos(conn: Connection) = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM geopos")
    resultSet.toStream.map(rs => rs.getString("id") + " "
      + rs.getString("msg_id") + " " + rs.getString("x") + " "
      + rs.getString("y") + " " + rs.getString("alt") + " " +
      rs.getString("time") + "\n").mkString("")
  }

  def lookDBMsg(conn: Connection): Unit = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM msg")
    println("id msg drone temp time MSG")
    println(resultSet.toStream.map(rs => rs.getString("id") + " "
      + rs.getString("msg_id") + " " + rs.getString("drone_id") + " "
      + rs.getString("temp") + " " + rs.getString("time") + " " +
      rs.getString("msg_type") + "\n").mkString(""))

  }

  def lookDBGeoPos(conn: Connection): Unit = {
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM geopos")
    println("   id msg  x  y  alt  time")
    println(resultSet.toStream.map(rs => rs.getString("id") + " "
      + rs.getString("msg_id") + " " + rs.getString("x") + " "
      + rs.getString("y") + " " + rs.getString("alt") + " " +
      rs.getString("time") + "\n").mkString(""))
  }

}
