name := "cacao"

description := "An Scala-based drawing interface"

scalaVersion := "2.11.1"

///////////////////////////////////////////////////////////////////////////////////////////////////
// PROJECT SETTINGS

lazy val cacao = project in file(".") dependsOn uqbarMath
lazy val uqbarMath = uri("git://github.com/uqbar-project/uqbar-math.git")

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "[2.2,)" % "test",
    "org.uqbar" %% "uqbar-math" % "1.1.0-SNAPSHOT"
)

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)

scalacOptions += "-feature"

///////////////////////////////////////////////////////////////////////////////////////////////////
// ECLIPSE SETTINGS

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true

///////////////////////////////////////////////////////////////////////////////////////////////////
// PUBLISHING SETTINGS

releaseSettings

crossScalaVersions := Seq(scalaVersion.value)

publishTo := Some(Resolver.file("Local Maven Repository",  file(Path.userHome.absolutePath + "/.m2/repository")))