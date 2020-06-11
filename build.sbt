
val sparkVersion = "2.4.0"
val json4sVersion = "3.6.0-M2"
val jodaVersion = "2.9.9"
val curatorVersion = "4.2.0"
val jacksonVersion = "2.10.0" //"2.7.9"
val apacheHttpVersion = "4.5.5"

val myDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "joda-time" % "joda-time" % jodaVersion,
  "org.apache.curator" % "curator-framework" % curatorVersion,
  "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-smile" % jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % jacksonVersion,
  "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-smile-provider" % jacksonVersion,
  "org.apache.httpcomponents" % "httpclient" % apacheHttpVersion
)

lazy val commonSettings = Seq(
  organization := "org.rzlabs",
  version := "0.1.1-SNAPSHOT",

  scalaVersion := "2.12.10"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "spark-druid-connector",
    libraryDependencies ++= myDependencies
  )

assemblyMergeStrategy in assembly := {
  case "reference.conf" => MergeStrategy.concat
  case PathList("META-INF", inf@_*) =>
    inf map {
      _.toLowerCase
    } match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps@(x :: inf) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: inf =>
        MergeStrategy.discard
      case "services" :: inf =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.first
    }
  case _ => MergeStrategy.first
}