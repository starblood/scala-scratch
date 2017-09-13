name := "scala-scratch"

version := "1.0"

scalaVersion := "2.12.3"

autoScalaLibrary := false

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % "2.12.3",
  "com.typesafe.akka" %% "akka-actor" % "2.5.4"
)
