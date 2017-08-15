name := """scrawler"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")) //.enablePlugins(PlayScala)

scalaVersion := "2.12.2"

//resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
resolvers += Resolver.sonatypeRepo("releases")
resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.4" % Test,
  "com.typesafe.akka" %% "akka-stream" % "2.5.4",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.4" % Test,
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9" % Test,
  "org.jsoup" % "jsoup" % "1.8.3",
  "io.javaslang" % "javaslang" % "2.0.2",
  "com.google.inject" % "guice" % "4.1.0",

  "junit" % "junit" % "4.12"
  )

//libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
//libraryDependencies += ws

//libraryDependencies += "org.webjars" % "flot" % "0.8.3"
//libraryDependencies += "org.webjars" % "bootstrap" % "3.3.6"
//
//libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
//libraryDependencies += "org.awaitility" % "awaitility" % "3.0.0" % Test
// for debugging sbt problems
logLevel := Level.Debug


