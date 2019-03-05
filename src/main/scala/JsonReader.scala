/*
I choose to use a functional oriented library for json parsing
  Argonaut
 */

import argonaut.{CodecJson, Json, Parse}
import scala.io.Source
import java.io.File

object JsonReader extends App {

  /**
    * getDirFiles
    *
    * @param dir - Given Directory
    * @return
    * From a directory given as a File : Give back all files in this dir
    */
  def getDirFiles(dir: File): List[File] = dir match {
    case d if d.exists && d.isDirectory => d.listFiles.filter(_.isFile).toList
    case _ => List[File]()
  }

  /**
    * getJsonFromFiles
    *
    * @param list - List of files (some may not be .json)
    * @return a List of Iterators of each files : Iterators contains each Json row of the file
    */
  def getJsonFromFiles(list: List[File]): List[Iterator[Either[String, Json]]] = {
    list.filter(f => ".+\\.json".r.findFirstMatchIn(f.toString).isDefined).map(x => getJSONbyMapLines(getFileLines(x.toString)))
  }

  /**
    *
    * @param path Path to a file
    * @return
    */
  def getFileLines(path: String): Iterator[String] = {
    Source.fromFile(path).getLines().map(x => x)
  }

  /**
    * Parse each line as Json
    * @param lines An Iterator of Strings = lines of a file
    * @return Iterator over Either[String=Error, Json], get it with Right
    */
  def getJSONbyMapLines(lines: Iterator[String]): Iterator[Either[String, Json]] = {
    lines.map(x => Parse.parse(x))
  }

}