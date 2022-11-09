import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._

name := """ada"""
ThisBuild / organization := "ada.cx"

// ScalaFix
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := "4.5.13"
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

// Reload Sbt on changes to sbt or dependencies
Global / onChangedBuildSource := ReloadOnSourceChanges

// Jib container
val jibEnv = settingKey[String]("env for docker images")
val jibSettings = List(
  jibBaseImage := "openjdk:17.0.2",
  jibBaseImageCredentialHelper := Some("docker-credential-gcloud"),
  jibTargetImageCredentialHelper := Some("docker-credential-gcloud"),
  jibRegistry := "gcr.io",
  jibName := s"${name.value}-${(Global / jibEnv).value}",
  jibOrganization := (Global / jibOrganization).value,
  jibVersion := (Global / version).value,
  jibJvmFlags ++= List(
    "-XX:+UseZGC"
  )
)

promptTheme := PromptTheme(
  List(
    text(_ => "[ada]", fg(64)).padRight(" λ ")
  )
)

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8", // source files are in UTF-8
  "-deprecation", // warn about use of deprecated APIs
  "-unchecked", // warn about unchecked type parameters
  "-feature", // warn about misused language features
  "-language:higherKinds", // allow higher kinded types without `import scala.language.higherKinds`
  "-language:postfixOps",
  "-Xlint", // enable handy linter warnings
  "-Xfatal-warnings", // turn compiler warnings into errors
  "-Ywarn-unused"
)

lazy val testSettings: Seq[Def.Setting[_]] = List(
  Test / parallelExecution := false,
  publish / skip := true,
  fork := true
)

lazy val root = project
  .in(file("."))
  .aggregate(`ada`)
  .settings(noPublish)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  publish / skip := true
)

lazy val `ada` = project
  .in(file("core"))
  .settings(
    testSettings,
    organization := "ada.cx",
    name := "ada-analytics",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "3.1.3",
    Defaults.itSettings,
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.circeCore,
      Libraries.circeGeneric,
      Libraries.circeParser,
      Libraries.doobieCore,
      Libraries.doobieHikari,
      Libraries.http4sCirce,
      Libraries.http4sDsl,
      Libraries.http4sServer,
      Libraries.log4CatsSlf4j,
      Libraries.logback,
      Libraries.scalaTest % Test,
      Libraries.scalaCheck % Test,
      Libraries.sqlite
    ),
    (Compile / mainClass) := Some("Main")
  )

// CI build
addCommandAlias("buildAda", ";clean;+test;")

// ScalaFormat + ScalaFix
addCommandAlias("format", ";scalafixAll ;scalafmtAll ;scalafmtSbt")
