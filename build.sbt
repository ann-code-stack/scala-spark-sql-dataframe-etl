name := "spark2-ml-workshop"

version := "1.0"
val sparkVersion= "2.2.0"
scalaVersion := "2.11.8"
libraryDependencies ++= Seq(
   "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7",
   "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7",
   "org.apache.spark" %% "spark-core" % sparkVersion,
   "org.apache.spark" %% "spark-sql"  % sparkVersion,
   "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
   "com.typesafe.play" %% "play-json" % "2.6.8",
   "com.typesafe" % "config" % "1.2.0",
   "mysql" % "mysql-connector-java" % "5.1.16"
)





