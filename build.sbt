name := """sse"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,
  "com.mandrillapp.wrapper.lutung" % "lutung" % "0.0.5",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars.bower" % "datatables-responsive" % "1.0.6"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
