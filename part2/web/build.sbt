name := "web"

version := "0.1"

scalaVersion := "2.12.8"

fork in (IntegrationTest, run) := true

trapExit := false


libraryDependencies ++= Seq(
 "io.argonaut" %% "argonaut" % "6.2.2",
 "com.typesafe.akka" %% "akka-http"   % "10.1.7",
 "com.typesafe.akka" %% "akka-stream" % "2.5.19",
 "org.scalatest" %% "scalatest" % "3.0.5" % "test",
 "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
)
