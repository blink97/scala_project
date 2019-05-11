import argonaut._
import Argonaut._

import scala.util.Random
import java.time.Instant
import java.sql.Timestamp

/**
  * Drone Messages Class
  */
object MsgClass extends App {

  val randIdMax = 1000000
  val randIdMsg = 1000000
  val randTempD = 5
  val randTempH = 130
  val randGeo = 100000

  /**
    * Drone Geographical Position Class/Tuple
    *
    * @param x   Int X Coord
    * @param y   Int Y Coord
    * @param alt Int Altitude in meters
    */
  case class GeoPos(x: Int, y: Int, alt: Int)

  /**
    * Drone Message Class
    *
    * @param droneId Int drone Id
    * @param msgId   Int message id
    * @param msgType String the message content
    * @param temp    Float Temperature
    * @param time    Long TimeStamp
    * @param geoPos  Object Geographical Position Object
    */
  case class Msg(
                  droneId: Int,
                  msgType: String,
                  temp: Float,
                  time: String,
                  // battery: Int
                  geoPos: GeoPos
                )

  /**
    * Give random message
    *
    * @return Msg
    */
  def MsgFactory(drone_id : Int = Random.nextInt(randIdMax)): Msg = {
    val randIdMax = 1000000
    val randTempD = 5
    val randTempH = 130
    val randGeo = 100000
    val drone_heat = (Random.nextInt(randTempH) + randTempD).toFloat
    if (drone_heat > 50)
      Msg(
        drone_id,
        "'HOT'",
        drone_heat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo),
          Random.nextInt(randGeo),
          Random.nextInt(4000)))
    else if (drone_heat < 10)
      Msg(
        drone_id,
        "'COLD'",
        drone_heat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo),
          Random.nextInt(randGeo),
          Random.nextInt(4000)))
    else
      Msg(
        drone_id,
        "'OK'",
        drone_heat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo),
          Random.nextInt(randGeo),
          Random.nextInt(4000)))
  }

  /**
    * Give random message
    *
    * @return Msg
    */
  def MsgFactory: Msg = {
    val randIdMax = 1000000
    val randTempD = -15
    val randTempH = 150
    val randGeo = 100000
    val drone_id = Random.nextInt(randIdMax)
    val drone_heat = (Random.nextInt(randTempH) + randTempD).toFloat
    val error_rate = Random.nextInt(100)
    if (error_rate >= 95)
      Msg(
        drone_id,
        "'ERROR'",
        drone_heat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo),
          Random.nextInt(randGeo),
          Random.nextInt(4000)))
    else if (drone_heat > 90)
      Msg(
        drone_id,
        "'HOT'",
        drone_heat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo),
          Random.nextInt(randGeo),
          Random.nextInt(4000)))
    else if (drone_heat < 10)
      Msg(
        drone_id,
        "'COLD'",
        drone_heat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo),
          Random.nextInt(randGeo),
          Random.nextInt(4000)))
    else
      Msg(
        drone_id,
        "'OK'",
        drone_heat,
        "'" + Timestamp.from(Instant.now).toString + "'",
        GeoPos(Random.nextInt(randGeo),
          Random.nextInt(randGeo),
          Random.nextInt(4000)))
  }

  /**
    * Used in MsgEncodeJson
    *
    * @return EncodeJson[GeoPos]
    */
  implicit def GeoPosEncodeJson: EncodeJson[GeoPos] = {
    EncodeJson((g: GeoPos) =>
      ("x" := g.x)
        ->: ("y" := g.y)
        ->: ("alt" := g.alt)
        ->: jEmptyObject
    )
  }

  /**
    * Give the message object Msg from Json
    * Use .asJson
    *
    * @return EncodeJson[Msg]
    */
  implicit def MsgEncodeJson: EncodeJson[Msg] = {
    EncodeJson((m: Msg) =>
      ("droneId" := m.droneId)
        ->: ("msgType" := m.msgType)
        ->: ("temp" := m.temp)
        ->: ("time" := m.time)
        ->: ("geoPos" := m.geoPos)
        ->: jEmptyObject)
  }

  /**
    * Get GeoPos from json
    *
    * @return DecodeJson[GeoPos]
    */
  implicit def GeoPosDecodeJson: DecodeJson[GeoPos] = {
    DecodeJson(g => for {
      x <- (g --\ "x").as[Int]
      y <- (g --\ "y").as[Int]
      alt <- (g --\ "alt").as[Int]
    } yield GeoPos(x, y, alt))
  }

  /**
    * Get Msg from Json
    * Access with  .getOr()
    *
    * @return DecodeJson[Msg]
    */
  implicit def MsgDecodeJson: DecodeJson[Msg] = {
    DecodeJson(m => for {
      droneId <- (m --\ "droneId").as[Int]
      msgType <- (m --\ "msgType").as[String]
      temp <- (m --\ "temp").as[Float]
      time <- (m --\ "time").as[String]
      geoPos <- (m --\ "geoPos").as[GeoPos]
    } yield Msg(droneId, msgType, temp, time, geoPos))
  }

}
