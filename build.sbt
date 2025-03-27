ThisBuild / organization := "com.mong"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version := "0.1.0-SNAPSHOT"

//val scalaVersion = "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "scala-scratch",
  )


libraryDependencies ++= Seq(
//  "org.apache.httpcomponents" %% "httpclient" % "4.5.14",
//  "commons-io" %% "commons-io" % "2.18.0"
)

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

