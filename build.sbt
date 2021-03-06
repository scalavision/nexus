import scala.io._

scalaVersion := "2.12.6"
enablePlugins(ScalaUnidocPlugin)


// credit to http://stackoverflow.com/a/32114551/2990673
lazy val mathFormulaInDoc = taskKey[Unit]("add MathJax script import in scaladoc html to display LaTeX formula")

lazy val commonSettings = Seq(

  organization := "me.tongfei",
  version := "0.1.0-SNAPSHOT",
  isSnapshot := true,
  scalaVersion := "2.12.6",

  scalacOptions += "-Ypartial-unification", // types

  libraryDependencies += "com.chuusai"   %% "shapeless" % "2.3.3",
  libraryDependencies += "org.typelevel" %% "algebra"   % "0.7.0",
  libraryDependencies += "org.typelevel" %% "cats-core" % "1.4.0",

  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  libraryDependencies += "me.tongfei"    %% "poly-io"   % "0.3.2" % Test,

  mathFormulaInDoc := {
    val apiDir = (doc in Compile).value
    val docDir = apiDir    // /"some"/"subfolder"  // in my case, only api/some/solder is parsed
    // will replace this "importTag" by "scriptLine
    // find all html file and apply patch
    if(docDir.isDirectory)
      for (f <- listHtmlFile(docDir)) {
        val content = Source.fromFile(f).getLines().mkString("\n")
        val writer = new java.io.PrintWriter(f)
        writer.write(content.replace(
          "<head>",
          """<head>
             |  <script type="text/x-mathjax-config">
             |    MathJax.Hub.Config({
             |      asciimath2jax: { delimiters: [['[[', ']]']] }
             |    });
             |  </script>
             |  <script type="text/javascript" async
             |    src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-MML-AM_CHTML">
             |  </script>""".stripMargin))
        writer.close()
      }
  },

  // attach this task to doc task
  mathFormulaInDoc <<= mathFormulaInDoc triggeredBy (doc in Compile)
)


lazy val tensor = (project in file("tensor"))
  .settings(commonSettings: _*)
  .settings(
    name := "nexus-tensor"
  )

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
  .dependsOn(tensor)
  .settings(
    name := "nexus-core"
  )

lazy val testBase = (project in file("test-base"))
  .settings(commonSettings: _*)
  .dependsOn(core)
  .settings(
    name := "nexus-test-base",
    publishArtifact := false,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4"
  )

lazy val ml = (project in file("ml"))
  .settings(commonSettings: _*)
  .dependsOn(core)
  .settings(
    name := "nexus-ml"
  )

lazy val tensorboard = (project in file("tensorboard"))
  .settings(commonSettings: _*)
  .dependsOn(core)
  .settings(
    name := "nexus-tensorboard"
  )

lazy val jvmRefBackend = (project in file("jvm-ref-backend"))
  .settings(commonSettings: _*)
  .dependsOn(core)
  .settings(
    name := "nexus-jvm-ref-backend"
  )

lazy val torchJni = (project in file("torch/jni"))
  .settings(commonSettings: _*)
  .settings(
    name := "nexus-torch-backend-jni"
  )

lazy val torchCpu = (project in file("torch/cpu"))
  .settings(commonSettings: _*)
  .dependsOn(tensor).dependsOn(torchJni)
  .settings(
    name := "nexus-torch-backend-cpu"
  )

lazy val torchCuda = (project in file("torch/cuda"))
  .settings(commonSettings: _*)
  .dependsOn(tensor).dependsOn(torchCpu)
  .settings(
    name := "nexus-torch-backend-cuda"
  )


// function that find html files recursively
def listHtmlFile(dir: java.io.File): List[java.io.File] = {
  dir.listFiles.toList.flatMap { f =>
    if(f.getName.endsWith(".html")) List(f)
    else if(f.isDirectory)          listHtmlFile(f)
    else                            List[File]()
  }
}
