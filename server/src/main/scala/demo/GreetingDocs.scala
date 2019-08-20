package demo

import endpoints.openapi
import endpoints.openapi.model.Info

object GreetingDocs extends Greeting with openapi.Endpoints with openapi.JsonSchemaEntities {

  val documentation =
    openApi(Info("Scala User Group Greeting", "1.0.0"))(hello)

}
