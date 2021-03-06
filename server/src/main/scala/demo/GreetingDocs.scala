package demo

import endpoints4s.akkahttp.server
import endpoints4s.openapi
import endpoints4s.openapi.model.{Info, OpenApi}

object GreetingDocs extends Greeting with openapi.Endpoints with openapi.JsonEntitiesFromSchemas {

  val documentation =
    openApi(Info("Scala User Group Greeting", "1.0.0"))(hello)

}

object DocumentationServer extends server.Endpoints with server.JsonEntitiesFromEncodersAndDecoders {

  val route =
    endpoint(get(path / "documentation"), ok(jsonResponse[OpenApi]))
      .implementedBy(_ => GreetingDocs.documentation)

}
