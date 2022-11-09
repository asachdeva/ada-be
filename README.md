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

## Challenge 1
Make curl calls to retrieve parsed messages like so:
`curl http://localhost:5005/messages`

## Challenge 2
Make curl call to get answers ike so:
```sh
curl -d '{"query":"Star Trek"}' -H "Content-Type: application/json" -X POST http://localhost:5005/search
```

Make curl call to get full answers ike so:
```sh
curl -d '{"query":"Star Trek"}' -H "Content-Type: application/json" -X POST http://localhost:5005/fullSearch
```

_Note_:  There is CI (github actions) job enabled for this repository.


## ScalaFmt and ScalaFix
To apply scalafmt and scalafix prior to push run
```bash
sbt format
```
