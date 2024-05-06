ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "Laba5",
    idePackagePrefix := Some("ru.paskal.laba5")
  )

libraryDependencies += "org.json4s" %% "json4s-jackson" % "4.0.7"
