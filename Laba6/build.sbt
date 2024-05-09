ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "Laba6",
    idePackagePrefix := Some("ru.paskal.laba6")
  )
