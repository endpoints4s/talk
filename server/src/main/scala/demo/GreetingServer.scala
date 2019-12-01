package demo

import akka.http.scaladsl.server.Directives._
import endpoints.akkahttp.server
import endpoints.openapi.model.{OpenApi, OpenApiSchemas}

object GreetingServer extends Greeting with server.Endpoints with server.playjson.JsonSchemaEntities with OpenApiSchemas {

  val documentation =
    endpoint(get(path / "documentation"), ok(jsonResponse[OpenApi]))

  val routes =
    hello.implementedBy { sug =>
      s"Hello ${sug.people} folks at ${sug.name}!"
    } ~
    documentation.implementedBy(_ => GreetingDocs.documentation)

}
