resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository".at("https://repo.typesafe.com/typesafe/releases/")

addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.1")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
addSbtPlugin("de.gccc.sbt" % "sbt-jib" % "1.0.0")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.3")
addSbtPlugin("com.scalapenos" % "sbt-prompt" % "1.0.2")
addSbtPlugin("org.scalameta" % "sbt-munit" % "0.7.29")
