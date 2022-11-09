ada-challenge
===================

[![Actions Status](https://github.com/asachdeva/ada-challenge/workflows/Build/badge.svg)](https://github.com/asachdeva/ada-challenge/actions)
<a href="https://typelevel.org/cats/"><img src="https://typelevel.org/cats/img/cats-badge.svg" height="40px" align="right" alt="Cats friendly" /></a>

Narrative challenge -- see reqts.md.
Tech Stack:
* CE + Cats (IO effect type + FP Abstractions for Foldable)
* circe (json encoding and decoding and parse failure)
* http4s (server + dsl + validating query params)
* scala 3.1.3

## Usage and Running (in a sbt shell)
* `sbt`
* `clean compile`
* `narrative/run`

Make curl calls to add events like so:
`curl -X POST 'http://localhost:8080/analytics?timestamp=10032342000&user=user2&event=click'`
`curl -X POST 'http://localhost:8080/analytics?timestamp=10032342000&user=user3&event=click'`
`curl -X POST 'http://localhost:8080/analytics?timestamp=10032342000&user=user2&event=impression'`
`curl -X POST 'http://localhost:8080/analytics?timestamp=10032342000&user=user3&event=impression'`
`curl -X POST 'http://localhost:8080/analytics?timestamp=10032342000&user=user4&event=click'`

Make curl call to get summary stats like so:
`curl 'http://localhost:8080/analytics?timestamp=10032342000'`

Note:  There is a CI (github actions) job enabled for this repo.

## ScalaFmt and ScalaFix
To apply scalafmt and scalafix prior to push run
```bash
sbt format
```
