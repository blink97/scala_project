/*
I choose to use a functional oriented library for json parsing
  Argonaut
 */

import argonaut.{CodecJson, Json, Parse}
import argonaut._
import scalaz._
import ArgonautScalaz._

import scala.io.Source
import java.io.File

import MsgClass.Msg

object JsonReader extends App {

  /**
    * getListOfFiles
    * @param dir - Given Directory
    * @return
    * From a directory given as a File : Give back all files in this dir
    */
  def getListOfFiles(dir: File): List[File] = dir match {
    case d if d.exists && d.isDirectory => d.listFiles.filter(_.isFile).toList
    case _ => List[File]()
  }

  /**
    * getJsonFromFiles
    * @param list - List of files (some may not be .json)
    * @return a List of Iterators of each files : Iterators contains each Json row of the file
    */
  def getJsonFromFiles(list: List[File]): List[Iterator[Either[String, Json]]] = {
    list.filter(f => ".+\\.json".r.findFirstMatchIn(f.toString).isDefined).map(x => getJSONbyMapLines(getFileLines(x.toString)))
  }

  /**
    *
    * @param path
    * @return
    */
  def getFileLines(path: String): Iterator[String] = {
    Source.fromFile(path).getLines().map(x => x)
  }

  /**
    *
    * @param lines
    * @return
    */
  def getJSONbyMapLines(lines: Iterator[String]): Iterator[Either[String, Json]] = {
    lines.map(x => Parse.parse(x))
  }

  implicit def MsgCodecJson: CodecJson[Msg] =
    casecodec3(Msg.apply, Msg.unapply)("droneId", "msgId", "msgType", "temp", "geoPos")

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

  println("Individual Json Output")
  println(elt1)
  println(elt2)

  // Quick Test dir Jsons
  // List of Either
  val resDir = getJsonFromFiles(getListOfFiles(new File("./")))
  println("(Folder) All Json Files Every Row Output")
  resDir.foreach(x => x.foreach { case Right(s) => println(s) case Left(e) => println(e) })
}