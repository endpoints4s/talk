package demo

import akka.http.scaladsl.server.Directives._
import play.twirl.api._
import demo.twirl.Implicits._

object WebInterface {

  val routes = {
    pathSingleSlash {
      get {
        complete {
          html"""
            <!DOCTYPE html>
            <html>
              <head>
                <link rel="icon" type="image/png" href="/assets/favicon.ico">
                <title>Endpoints Demo</title>
              </head>
              <body>
                <h1>Endpoints Demo</h1>

                <form id="say-hello">
                  <input type="text" name="name" placeholder="Name">
                  <button type="button" onclick="demo.sayHello()">Say Hello</button>
                </form>
                <p id="say-hello-result"></p>

                ${scalajs.html.scripts("client", name => s"/assets/$name", name => getClass.getResource(s"/public/$name") != null)}
              </body>
            </html>"""
        }
      }
    } ~
    pathPrefix("assets" / Remaining) { file =>
      // optionally compresses the response with Gzip or Deflate
      // if the client accepts compressed responses
      encodeResponse {
        getFromResource("public/" + file)
      }
    }
  }
}
