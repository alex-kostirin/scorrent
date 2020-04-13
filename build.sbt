name := "scorrent"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "org.rogach" %% "scallop" % "3.4.0"
)