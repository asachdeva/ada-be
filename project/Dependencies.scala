import sbt._

object Dependencies {

  object Versions {
    val cats = "2.8.0"
    val catsEffect = "3.3.14"
    val circe = "0.15.0-M1"
    val doobie = "1.0.0-RC2"
    val http4s = "1.0.0-M37"
    val log4Cats = "2.5.0"
    val sqlite = "3.39.3.0"

    // Test
    val scalaTest = "3.2.14"
    val scalaCheck = "1.17.0"

    // Runtime
    val logback = "1.4.4"
  }

  object Libraries {
    def circe(artifact: String): ModuleID = "io.circe" %% s"circe-$artifact" % Versions.circe
    def doobie(artifact: String): ModuleID = "org.tpolecat" %% s"doobie-$artifact" % Versions.doobie
    def http4s(artifact: String): ModuleID = "org.http4s" %% s"http4s-$artifact" % Versions.http4s
    def log4cats(artifact: String): ModuleID = "org.typelevel" %% s"log4cats-$artifact" % Versions.log4Cats

    lazy val cats = "org.typelevel" %% "cats-core" % Versions.cats
    lazy val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect

    lazy val circeCore = circe("core")
    lazy val circeGeneric = circe("generic")
    lazy val circeParser = circe("parser")
    lazy val circeGenericX = circe("generic-extras")

    lazy val doobieCore = doobie("core")
    lazy val doobieHikari = doobie("hikari")

    lazy val http4sCirce = http4s("circe")
    lazy val http4sDsl = http4s("dsl")
    lazy val http4sServer = http4s("ember-server")

    lazy val log4CatsCore = log4cats("core")
    lazy val log4CatsSlf4j = log4cats("slf4j")
    lazy val sqlite = "org.xerial" % "sqlite-jdbc" % Versions.sqlite

    // Test
    lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % Versions.scalaCheck
    lazy val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest
    lazy val doobieTest = doobie("scalatest")

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback
  }

}
