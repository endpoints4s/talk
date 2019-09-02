package demo

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLInputElement

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@JSExportTopLevel("demo")
object GreetingClient {

  @JSExport()
  def sayHello(): Unit = {
    val name = getInputValue("input[name=name]")
    // TODO Invoke the greeting endpoint
  }

  def getInputValue(selector: String): String =
    dom.document.querySelector(selector).asInstanceOf[HTMLInputElement].value

}
