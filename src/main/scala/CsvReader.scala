import java.io.File

object CsvReader extends App {

  /**
    * 
    * @param lines: each lines of the csv file
    * @return A list of lines (which are list of String, containing the elements of the line)
    */
  def getListFromFile(lines: Iterator[String]): List[List[String]] = lines match {
    case lines if !lines.hasNext => List()
    case _ => lines.next().split(",").map(_.trim).toList :: getListFromFile(lines)
  }

  /**
    *
    * @param path: of the file
    * @return A list of lines (which are list of String, containing the elements of the line)
    */
  def readCsv(path: String) = {
    getListFromFile(io.Source.fromFile(path).getLines())
  }

  /**
    *
    * @param dir: A object File() that is the directory containing all the csv we want
    * @return A list of objects File(), which are all the files of the directory
    */
  def getListOfFiles(dir: File):List[File] = dir match {
    case d if d.exists && d.isDirectory => d.listFiles.filter(_.isFile).toList
    case _ => List[File]()
  }

  /**
    *
    * @param list: A list of objects File()
    * @return A list of list containing @see getListFromFile
    */
  def getListOfCsv(list: List[File]): List[List[List[String]]] = list match {
    case Nil => List()
    case x :: tail if ".+\\.csv".r.findFirstMatchIn(x.toString).isDefined => readCsv(x.toString) :: getListOfCsv(tail)
    case _ :: tail => getListOfCsv(tail)
  }

  println(getListOfCsv(getListOfFiles(new File("./"))))
}
