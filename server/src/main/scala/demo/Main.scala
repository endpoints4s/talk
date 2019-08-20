package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object Main {

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("server-system")
    implicit val materializer: Materializer = ActorMaterializer()

    val interface = "0.0.0.0"
    val port = 8000
    val routes = WebInterface.routes ~ GreetingServer.routes
    val binding = Http().bindAndHandle(routes, interface, port)
    println(s"Server online at http://$interface:$port")

    sys.addShutdownHook {
      Await.result(Await.result(binding, 10.seconds).terminate(3.seconds), 15.seconds)
      Await.result(system.terminate(), 5.seconds)
    }
  }

}
