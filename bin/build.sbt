lazy val root = (project in file("."))
  .settings(
    name := "scala-crypto",

    version := "1.0",

    scalaVersion := "2.11.8",

    mainClass := Some("example.Main")
)
