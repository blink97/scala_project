import argonaut.Json
import org.scalajs.dom

import scala.scalajs.js.JSApp

object Html extends JSApp {
  def createTab(json: Json) = {
    val array = dom.document.createElement("tr")
    val cell = dom.document.createElement("td")
    array.appendChild(cell)
  }

  override def main(): Unit = ???
}