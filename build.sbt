lazy val commonSettings = Seq(
  organization := "org.opensirf.play",
  version := "1.0.0"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava).
  settings(
    name := "OpenSIRF Play",
    artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
      artifact.name + "-" + version.value + "." + artifact.extension
    },
    version := "1.0.0"
  )
  
libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.google.inject" % "guice" % "3.0",
  "com.google.inject.extensions" % "guice-assistedinject" % "4.0",
  "org.opensirf.jaxrs" % "opensirf-jax-rs" % "1.0.0",
  "org.opensirf.core" % "opensirf-core" % "1.0.0"
)

retrieveManaged := true

scalaVersion := "2.10.5"
routesGenerator := InjectedRoutesGenerator
EclipseKeys.preTasks := Seq(compile in Compile)

crossPaths := false
//publishTo := Some(Resolver.url("Artifactory Realm", new URL("http://200.144.189.109:58082/artifactory"))(Resolver.ivyStylePatterns))
//credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
//publishMavenStyle := false

publishArtifact in (Compile, packageDoc) := false
publishArtifact in (Compile, packageSrc) := false

resolvers += Resolver.url("SIRF Artifactory", url("http://200.144.189.109:58082/artifactory"))(Resolver.ivyStylePatterns)
