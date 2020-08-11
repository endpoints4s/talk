package demo

import endpoints4s.akkahttp.server

object GreetingServer extends Greeting with server.Endpoints with server.JsonEntitiesFromSchemas {

  val route =
    hello.implementedBy { sug =>
      s"Hello ${sug.people} folks at ${sug.name}!"
    }

}
