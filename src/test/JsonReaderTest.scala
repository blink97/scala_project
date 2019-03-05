import java.io.File
import JsonReader.{getFileLines, getJSONbyMapLines, getJsonFromFiles, getListOfFiles}
import org.scalatest.FunSuite

object JsonReaderTest extends FunSuite {
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
  test("Json parsed correctly") {
    assert(elt1.toString == "{\"DataName\":\"Digits\",\"DataType\":\"Numbers\",\"Data\":[0,1,2,3,4,5,6,7,8,9]}")
    assert(elt2.toString == "{\"DataName\":\"Weapons\",\"DataType\":\"List of Objects\",\"Data\":[{\"Name\":\"MP5\",\"Origin\":\"Germany\",\"Ammo\":\"9mm Parabellum\"},{\"Name\":\"FN FiveSeven\",\"Origin\":\"Belgium\",\"Ammo\":\"5.7mm\"},{\"Name\":\"FN P90\",\"Origin\":\"Belgium\",\"Ammo\":\"5.7mm\"}]}")
  }

  // Quick Test dir Jsons
  // List of Either
  val resDir = getJsonFromFiles(getListOfFiles(new File("./")))
  println("(Folder) All Json Files Every Row Output")
  resDir.foreach(x => x.foreach { case Right(s) => println(s) case Left(e) => println(e) && assert(e != "") })
}