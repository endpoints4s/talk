import java.io.File

import sbt.io.IO

import scala.sys.process._

class Slides(source: File, resources: Seq[File], target: File, slidy: File) {
  private val targetDirectory = target.getParentFile
  private var runningServer: Option[Process] = None
  def startServer(): Unit = {
    runningServer.foreach(_.destroy())
    compile()
    runningServer = Some(Process("python3 -m http.server", targetDirectory).run())
    println("Slides served at http://localhost:8000")
  }
  def stopServer(): Unit = {
    runningServer.foreach { server =>
      println("Stopping HTTP server")
      server .destroy()
    }
    runningServer = None
  }
  def compile(): Unit = {
    IO.createDirectory(targetDirectory)
    val slidyTargetDirectory = new File(targetDirectory, slidy.getName)
    if (!slidyTargetDirectory.exists()) {
      IO.copyDirectory(slidy, slidyTargetDirectory)
    }
    IO.copy(resources.map(file => (file, new File(targetDirectory, file.getName))))
    s"pandoc --standalone --slide-level=3 --number-sections --variable slidy-url=${slidy.getName} --smart -r markdown -w slidy -o ${target.getAbsolutePath} ${source.getAbsolutePath}".!
  }
  override def finalize(): Unit = stopServer()
}
