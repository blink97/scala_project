object Main extends App {

  def getListFromFile(lines: Iterator[String]): List[List[String]] = lines match {
    case lines if !lines.hasNext => List()
    case _ => lines.next().split(",").map(_.trim).toList :: getListFromFile(lines)
  }

  def readCsv(path: String) = {
    getListFromFile(io.Source.fromFile(path).getLines())
  }

  println(readCsv("test.csv"))
}
