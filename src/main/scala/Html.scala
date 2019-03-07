import argonaut.Json
import org.scalajs.dom

import scala.scalajs.js.JSApp

object Html extends JSApp {
  /*def createTab() = {
    val json = JsonReader.getJSONbyMapLines(JsonReader.getFileLines("testJsonV2.json"))
      .collect { case Right(value) => value }.next().toString()
    println(json)
    /*val array = dom.document.createElement("tr")
    val cell = dom.document.createElement("td")
    array.appendChild(cell)*/
  }*/
  def main(): Unit = {
    val json = JsonReader.getJSONbyMapLines(JsonReader.getFileLines("testJsonV2.json"))
      .collect { case Right(value) => value }.next().toString()
    println(json)
  }
}