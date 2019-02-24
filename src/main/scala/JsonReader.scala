/*
I choose to use a functional oriented library for json parsing
  Argonaut
 */

import argonaut._
import java.io.File
import scala.io.Source


object JsonReader extends App {

  def getFileLines(path: String): Iterator[String] = {
    Source.fromFile(path).getLines().map(x => x)
  }

  def getJSONbyMapLines(lines: Iterator[String]): Iterator[Either[String, Json]] = {
    lines.map(x => Parse.parse(x))
  }

  val res = getJSONbyMapLines(getFileLines("testJson.json"))

  println(res.next() match {case Right(s) => s case Left(e) => e})
}