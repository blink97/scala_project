import argonaut._, Argonaut._

object MsgClass extends App {

  case class GeoPos(x: Int, y: Int, alt: Int)

  case class Msg(
                  droneId: Int,
                  msgId: Int,
                  msgType: String,
                  temp: Float,
                  geoPos: GeoPos
                )

  implicit def GeoPosEncodeJson: EncodeJson[GeoPos] = {
    EncodeJson((g: GeoPos) =>
      ("x" := g.x)
        ->: ("y" := g.y)
        ->: ("alt" := g.alt)
        ->: jEmptyObject
    )
  }

  implicit def MsgEncodeJson: EncodeJson[Msg] = {
    EncodeJson((m: Msg) =>
      ("droneId" := m.droneId)
        ->: ("msgId" := m.msgId)
        ->: ("msgType" := m.msgType)
        ->: ("temp" := m.temp)
        ->: ("geoPos" := m.geoPos)
        ->: jEmptyObject)
  }

  implicit def GeoPosDecodeJson: DecodeJson[GeoPos] = {
    DecodeJson(g => for {
      x <- (g --\ "x").as[Int]
      y <- (g --\ "y").as[Int]
      alt <- (g --\ "alt").as[Int]
    } yield GeoPos(x, y, alt))
  }

  implicit def MsgDecodeJson: DecodeJson[Msg] = {
    DecodeJson(m => for {
      droneId <- (m --\ "droneId").as[Int]
      msgId <- (m --\ "msgId").as[Int]
      msgType <- (m --\ "msgType").as[String]
      temp <- (m --\ "temp").as[Float]
      geoPos <- (m --\ "geoPos").as[GeoPos]
    } yield Msg(droneId, msgId, msgType, temp, geoPos))
  }

  // Test
  val kJson = Msg(1, 1, "Error", 23.6f, GeoPos(34243, 23224, 232))
  println(kJson)
  println(kJson.asJson)
  val jsonK = kJson.asJson
  println(jsonK)
  println(jsonK.as[Msg])
  val msg = jsonK.as[Msg]
  println("Test : ", msg.getOr() == kJson)

}
