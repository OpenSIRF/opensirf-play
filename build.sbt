lazy val commonSettings = Seq(
  organization := "org.opensirf.play",
  version := "1.0.0"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava).
  settings(
    name := "OpenSIRF JAX-RS",
    artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
      artifact.name + "-" + version.value + "." + artifact.extension
    },
    version := "1.0.0"
  )
  

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.google.inject" % "guice" % "3.0"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

EclipseKeys.preTasks := Seq(compile in Compile)
