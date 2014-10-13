name := "cacao"

description := "An Scala-based drawing interface"

scalaVersion := "2.11.1"

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val cacao = FDProject(
    "org.uqbar" %% "uqbar-math" % "[1.1.0-SNAPSHOT)",
    "org.scalatest" %% "scalatest" % "[2.2,)" % "test"
)

///////////////////////////////////////////////////////////////////////////////////////////////////

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)

scalacOptions += "-feature"