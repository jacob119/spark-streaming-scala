name := "spark-streaming-scala"

version := "0.1"

scalaVersion := "2.11.0"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"


libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.1.0"
//libraryDependencies += "dibbhatt" % "kafka-spark-consumer" % "1.0.11"
libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "2.0.0"


