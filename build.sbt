name := "scala-template"

organization := "com.example"

version := "1.0.1-SNAPSHOT"

scalaVersion := "2.11.2"

val buildNumber = settingKey[Int]("Current Build Number")

buildNumber := {
    val file: File = baseDirectory.value / "project/build.properties"
    val prop = new java.util.Properties
    def readProp: Int = {  
      prop.load(new java.io.FileInputStream(file))
      prop.getProperty("build.number", "0").toInt
    }
    def writeProp(value: Int) {
      prop.setProperty("buildnumber", value.toString)
      prop.store(new java.io.FileOutputStream(file), null)
    }
    val current = if (file.exists) readProp
                  else 0
    writeProp(current + 1)
    current
}

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "-" + scalaVersion.value + "-" + module.revision + "-" + buildNumber.value + "." + artifact.extension
}

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfuture", "-Xlint")

incOptions := incOptions.value.withNameHashing(true)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.specs2" %% "specs2" % "2.3.13" % "test",
  "com.assembla.scala-incubator" %% "graph-core" % "1.9.0",
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  "com.github.scala-blitz" %% "scala-blitz" % "1.1",
  "org.scala-lang" %% "scala-pickling" % "0.9.0-SNAPSHOT",
  "com.chuusai" %% "shapeless" % "2.0.0",
  "io.argonaut" %% "argonaut" % "6.0.4" 
)

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, buildInfoBuildNumber)

buildInfoPackage := organization + "info"