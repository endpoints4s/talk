package demo

import endpoints.akkahttp.server
import akka.http.scaladsl.server.Directives._
import endpoints.openapi
import endpoints.openapi.model.OpenApi

object GreetingServer extends Greeting with server.Endpoints with server.playjson.JsonSchemaEntities with openapi.model.OpenApiSchemas {

  val documentation =
    endpoint(get(path / "documentation"), jsonResponse[OpenApi]())

  val routes =
    hello.implementedBy { sug =>
      s"Hello ${sug.people} folks at ${sug.name}!"
    } ~
    documentation.implementedBy(_ => GreetingDocs.documentation)

}
