ada-be
===================

[![Actions Status](https://github.com/asachdeva/ada-be/workflows/Build/badge.svg)](https://github.com/asachdeva/ada-be/actions)
<a href="https://typelevel.org/cats/"><img src="https://typelevel.org/cats/img/cats-badge.svg" height="40px" align="right" alt="Cats friendly" /></a>

Narrative challenge -- see reqts.md.
Tech Stack:
* CE (Effect Type + Concurrency) + Cats (IO effect type + FP Abstractions)
* circe (json encoding and decoding and parse failures)
* doobie (database abstraction)
* http4s (server + dsl + validating query params)
* scala 3.1.3

## Usage and Running (in a sbt shell)
* `sbt`
* `clean compile`
* `ada/run`

## ScalaFmt and ScalaFix
To apply scalafmt and scalafix prior to push run
```bash
sbt format
```
