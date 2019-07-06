name := "scala_part2"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "2.2.1",
  "org.apache.kafka" % "kafka-clients" % "2.2.1",
  "org.apache.kafka" % "kafka-streams" % "2.2.1",
  "org.apache.kafka" %% "kafka-streams-scala" % "2.2.1",
  "org.apache.spark" %% "spark-core" % "2.4.3",
  "org.apache.spark" %% "spark-streaming" % "2.4.3"
)