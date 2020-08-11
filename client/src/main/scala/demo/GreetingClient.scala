package demo

import endpoints4s.xhr
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLInputElement

import scala.concurrent.Future
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.util.{Failure, Success}

@JSExportTopLevel("demo")
object GreetingClient extends Greeting with xhr.future.Endpoints with xhr.JsonEntitiesFromSchemas {

  @JSExport()
  def sayHello(): Unit = {
    val name = getInputValue("input[name=name]")
    val people = getInputValue("input[name=people]").toInt
    val eventualResponse: Future[String] = hello(ScalaUserGroup(name, people))
    eventualResponse.onComplete {
      case Success(response)  => dom.document.getElementById("say-hello-result").textContent = response
      case Failure(exception) => dom.console.error(s"Unable to reach the server, $exception")
    }
  }

  def getInputValue(selector: String): String =
    dom.document.querySelector(selector).asInstanceOf[HTMLInputElement].value

}
