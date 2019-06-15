import argonaut._
import Argonaut._
import org.scalatest.{FunSuite, run}

object RandomTest extends App {

  class Test extends FunSuite {
    // Test
    val strJson = List(Json(), Json(), Json()).toStream.map(x => x.toString()).mkString("\n")

    println(strJson)

    test("Json equals object") {
      assert(strJson == "{}\n{}\n{}")
    }

  }

  run(new Test)
}