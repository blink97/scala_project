/*
I choose to use a functional oriented library for json parsing
  Argonaut
 */

import argonaut.Json
import argonaut.Parse
import scala.io.Source


object JsonReader extends App {

  def getFileLines(path: String): Iterator[String] = {
    Source.fromFile(path).getLines().map(x => x)
  }

  def getJSONbyMapLines(lines: Iterator[String]): Iterator[Either[String, Json]] = {
    lines.map(x => Parse.parse(x))
  }

  // val resJson = Json.jEmptyObject-
  val res = getJSONbyMapLines(getFileLines("testJson.json"))
  val elt1 = res.next() match {
    case Right(s) => s
    case Left(e) => e
  }
  val elt2 = res.next() match {
    case Right(s) => s
    case Left(e) => e
  }

  println(elt1)
  println(elt2)
}