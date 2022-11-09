package api

import cats.*
import cats.effect.*
import cats.implicits.*

import io.circe.generic.auto.*
import io.circe.syntax.*

import model.*

import org.http4s.Status.*
import org.http4s.*
import org.http4s.circe.CirceEntityEncoder.*
import org.http4s.circe.*
import org.http4s.dsl.*
import org.http4s.dsl.io.*
import org.http4s.implicits.*
import repo.*

class BotService(botRepo: BotRepo):
  given searchQueryDecoder: QueryParamDecoder[SearchQuery] = QueryParamDecoder[String].emap(SearchQuery.parseSearchQuery)
  object SearchQueryParamMatcher extends ValidatingQueryParamDecoderMatcher[SearchQuery]("query")

  val routes = HttpRoutes
    .of[IO] {
      case GET -> Root / "messages" => Ok(botRepo.getAllMessages())
      case POST -> Root / "search" :? SearchQueryParamMatcher(maybeSearchQuery) => 
        maybeSearchQuery.fold(
          parseFailures => BadRequest(s"Invalid Request param query: ${parseFailures.map(_.sanitized)}"),
          searchQuery => botRepo.searchAnswers(searchQuery).flatMap(result => Ok(s"""${result}"""))
        )

    }
    .orNotFound
