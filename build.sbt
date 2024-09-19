ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "pps-23-24-flexibledessimulator",
    libraryDependencies ++= Seq(
      "com.github.sbt" % "junit-interface" % "0.13.3" % Test,
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "it.unibo.alice.tuprolog" % "tuprolog" % "3.3.0"
    ),

    assembly / mainClass := Some("MainMenuApp"), 
    assembly / assemblyJarName := "pps-23-24-flexibledessimulator.jar",
    Compile / run / mainClass := Some("MainMenuApp"),
    Compile / run / fork := true,
  )
