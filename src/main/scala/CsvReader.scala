import java.io.File

object CsvReader extends App {
  def getListFromFile(lines: Iterator[String]): List[List[String]] = lines match {
    case lines if !lines.hasNext => List()
    case _ => lines.next().split(",").map(_.trim).toList :: getListFromFile(lines)
  }

  def readCsv(path: String) = {
    getListFromFile(io.Source.fromFile(path).getLines())
  }

  def getListOfFiles(dir: File):List[File] = dir match {
    case d if d.exists && d.isDirectory => d.listFiles.filter(_.isFile).toList
    case _ => List[File]()
  }

  def getListOfCsv(list: List[File]): List[List[List[String]]] = list match {
    case Nil => List()
    case x :: tail if ".+\\.csv".r.findFirstMatchIn(x.toString).isDefined => readCsv(x.toString) :: getListOfCsv(tail)
    case _ :: tail => getListOfCsv(tail)
  }

  println(getListOfCsv(getListOfFiles(new File("./"))))
}
