name := "spark"
organization := "com.epita"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.8"

val sparkVersion = "2.4.3"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.apache.kafka" %% "kafka" % "2.2.1",
  "org.apache.kafka" % "kafka-clients" % "2.2.1",
  "org.apache.kafka" % "kafka-streams" % "2.2.1",
  "org.apache.kafka" %% "kafka-streams-scala" % "2.2.1",
  "org.apache.spark" %% "spark-core" % "2.4.3",
  "org.apache.spark" %% "spark-streaming" % "2.4.3",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.3"
  )

