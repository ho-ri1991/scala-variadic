import sbt.Keys._

val libVersion = "1.0"

val projectName = "scala-variadic"

val scala = "2.12.2"

crossScalaVersions := Seq("2.11.11", "2.12.2")

lazy val metaMacroSettings: Seq[Def.Setting[_]] = Seq(
  resolvers += Resolver.sonatypeRepo("releases"),
  resolvers += Resolver.bintrayIvyRepo("scalameta", "maven"),
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
    "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided"
  ),
  scalacOptions ++= Seq(
    "-Xplugin-require:macroparadise",
    "-Ymacro-debug-lite"
  )
)

def commonSettings(prjName: String) = Seq(
  name := prjName,
  scalaVersion := scala,
  version := libVersion
)

lazy val root = (project in file("."))
  .aggregate(scalaVariadic)

lazy val scalaVariadic = (project in file("./scala-variadic"))
  .settings(commonSettings("scala-variadic"))
  .settings(metaMacroSettings)
  .settings(libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0")// % "provided")

lazy val scalaMain = (project in file("./scala-main"))
  .settings(commonSettings("meta-main"))
  .settings(metaMacroSettings)
  //    .settings(libraryDependencies += groupId %% projectName % libVersion % "provided")
  .dependsOn(scalaVariadic)
