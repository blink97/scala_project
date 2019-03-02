
object MsgClass extends App {
  case class GeoPos(x: Int, y: Int, alt: Int)

  case class Msg(
                droneId: Int,
                msgId: Int,
                msgType: String,
                temp: Float,
                geoPos: GeoPos
                )
}
