import MsgClass.{GeoPos, Msg}
import argonaut._
import Argonaut._
import org.scalatest.{FunSuite, run}

object MsgClassTest extends App {
  class Test extends FunSuite {
    // Test
    val kJson = Msg(1, 1, "Error", 23.6f, "2007-01-01T00:00:00.00Z", GeoPos(34243, 23224, 232))
    // println(kJson)
    // println(kJson.asJson)
    val jsonK = kJson.asJson
    println(jsonK)
    // println(jsonK.as[Msg])
    val msg = jsonK.as[Msg]
    test("Json equals object") {
      assert(msg.getOr() == kJson)
    }
  }

  run(new Test)
}
