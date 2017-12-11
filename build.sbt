import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.latamautos",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "org.typelevel"  %% "cats-core"            % "1.0.0-MF",
      "org.typelevel"  %% "cats-free"            % "1.0.0-MF",
      "org.scalatest"  %% "scalatest"            % "3.0.1" % "test"
    )
  )
