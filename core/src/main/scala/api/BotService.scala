package api

import cats.*
import cats.effect.*
import cats.implicits.*

import io.circe.*
import io.circe.generic.auto.*
import io.circe.syntax.*

import org.http4s.Status.*
import org.http4s.*
import org.http4s.circe.CirceEntityDecoder.circeEntityDecoder
import org.http4s.circe.CirceEntityEncoder.*
import org.http4s.circe.*
import org.http4s.dsl.*
import org.http4s.dsl.io.*
import org.http4s.implicits.*

import model.*
import repo.*

class BotService(botRepo: BotRepo):

  object SearchQuery extends QueryParamDecoderMatcher[String]("query")

  val routes = HttpRoutes
    .of[IO] {
      case GET -> Root / "messages" => Ok(botRepo.getAllMessages())
      case req @ POST -> Root / "search" =>
        for {
          searchQuery <- req.as[SearchQuery]
          resp <- Ok(botRepo.searchAnswers(searchQuery.query))
        } yield resp
      case req @ POST -> Root / "fullSearch" =>
        for {
          searchQuery <- req.as[SearchQuery]
          resp <- Ok(botRepo.searchFullAnswers(searchQuery.query))
        } yield resp
    }
    .orNotFound
