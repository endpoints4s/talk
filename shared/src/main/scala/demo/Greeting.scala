package demo

import endpoints.algebra

trait Greeting extends algebra.Endpoints with algebra.JsonEntitiesFromSchemas {

//  val sugQuery: QueryString[ScalaUserGroup] =
//    (qs[String]("name") & qs[Int]("people")).xmap(ScalaUserGroup.tupled)(sug => (sug.name, sug.people))

  val hello: Endpoint[ScalaUserGroup, String] =
    endpoint(
//      get(path / "hello" /? sugQuery),
      post(path / "hello", jsonRequest[ScalaUserGroup], docs = Some("A Scala User Group")),
      ok(textResponse, docs = Some("A welcome message to the Scala User Group"))
    )

  implicit lazy val sugSchema: JsonSchema[ScalaUserGroup] = (
    field[String]("name", Some("Name of the SUG")) zip
    field[Int]("people", Some("Number of people in the SUG"))
  ).xmap(ScalaUserGroup.tupled)(sug => (sug.name, sug.people))

}

case class ScalaUserGroup(name: String, people: Int)
