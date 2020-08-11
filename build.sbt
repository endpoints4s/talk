import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

scalaVersion := "2.13.1"

name := "endpoints-talk"

val endpointsVersion = "1.0.0"

val shared =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .in(file("shared"))
    .settings(
      libraryDependencies += "org.endpoints4s" %%% "algebra" % endpointsVersion
    )

val client =
  project.in(file("client"))
    .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
    .dependsOn(shared.js)
    .settings(
      libraryDependencies += "org.endpoints4s" %%% "xhr-client" % endpointsVersion,
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.7"
    )

val server =
  project.in(file("server"))
    .enablePlugins(SbtWeb)
    .dependsOn(shared.jvm)
    .settings(
      libraryDependencies += "org.endpoints4s" %% "akka-http-server" % endpointsVersion,
      // Put client’s JavaScript artifact on the server’s classpath
      libraryDependencies += "com.vmunier" %% "scalajs-scripts" % "1.1.2",
      scalaJSProjects := Seq(client),
      pipelineStages in Assets := Seq(scalaJSPipeline),
      managedClasspath in Runtime += (packageBin in Assets).value,
      WebKeys.packagePrefix in Assets := "public/",
      compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value
    )

val slides = settingKey[Slides]("Utility class for rendering the slides")
slides := new Slides(
  baseDirectory.value / "slides" / "index.md",
  ((baseDirectory.value / "slides") * "*.png" +++ (baseDirectory.value / "slides") * "*.svg").get(),
  target.value / "slides" / "index.html",
  baseDirectory.value / "slides" / "slidy"
)

val makeSlides = taskKey[Unit]("Compile the slides")
makeSlides := slides.value.compile()

val showSlides = taskKey[Unit]("Show the slides in a web browser")
showSlides := slides.value.startServer()

watchSources += WatchSource(baseDirectory.value / "slides")

(onLoad in Global) := {
  (onLoad in Global).value.compose(
    _.addExitHook {
      slides.value.stopServer()
    }
  )
}
