scalaVersion := "2.12.3"
lazy val logReader = (project in file("."))
  .settings(
    name := "LogReader",
    libraryDependencies += "net.liftweb" %% "lift-json" % "3.3.0"
  )