name := """play-metrics-module"""

version := "1.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

organization := "si.poponline"

organizationName := "poponline.si"

organizationHomepage := Some(new URL("http://24ur.com"))

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
publishTo := {
    Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

startYear := Some(2016)

description := "route metrics for Playframework."

licenses := Seq("The Apache Software License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))



pomExtra :=
  <url>https://github.com/samek/play-metrics-module</url>
  <scm>
    <url>https://github.com/samek/play-metrics-module</url>
    <connection>scm:git:git@github.com/samek/play-metrics-module.git</connection>
    <developerConnection>scm:git:https://github.com/samek/play-metrics-module.git</developerConnection>
  </scm>
    <developers>
      <developer>
        <id>samek</id>
        <name>samek</name>
      </developer>
    </developers>

scalacOptions := Seq("-feature", "-deprecation")
