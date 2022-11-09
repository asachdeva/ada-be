package repo

import cats.*
import cats.effect.*
import cats.implicits.*

import doobie.*
import doobie.implicits.*
import doobie.util.transactor.Transactor

import io.circe.*

import model.*

class BotQueries(transactor: Transactor[IO]) {

  def parseRawMessages(rawMessages: List[String], allStateIdsMap: Map[String, String]) = {
    def secondParse(raw: String, allStateIdsMap: Map[String, String]): String =
      raw match
        case s"$head{$stateId|}$tail" if allStateIdsMap.contains(stateId) =>
          head + allStateIdsMap.get(stateId).get + tail
        case s"$head{$stateId|$default}$tail" =>
          head + default + tail

    rawMessages.map {
      case s"$head{$stateId|}$tail" if allStateIdsMap.contains(stateId) =>
        head + allStateIdsMap.get(stateId).get + tail
      case s"$head{$stateId|$default}$tail" if allStateIdsMap.contains(stateId) =>
        val stem = allStateIdsMap.get(stateId).get
        val maybe = head + stem + tail.replace(s"{$stateId|}", stem)
        if (maybe.contains("{")) secondParse(maybe, allStateIdsMap) else maybe
      case s"$head{$stateId|$default}$tail" =>
        val maybe = head + default + tail
        if (maybe.contains("{")) secondParse(maybe, allStateIdsMap) else maybe
    }.toList
  }

  def getMessages: IO[List[String]] =
    for {
      allStateIdsMap <- BotQueries.allStateIdsAsMapForQuery().to[List].transact(transactor).map(_.toMap)
      rawMessageBodies <- BotQueries.allMessageBodiesForQuery().to[List].transact(transactor)
      parsedMessages = parseRawMessages(rawMessageBodies, allStateIdsMap)
    } yield parsedMessages

  def getAnswers(query: String): IO[Answer] = BotQueries.answersQuery(query).option.transact(transactor).map(_.getOrElse(Answer(0, "")))

}

object BotQueries {
  def allMessageBodiesForQuery() = {
    val statement =
      sql"""SELECT body FROM messages""".stripMargin
    statement.query[String]
  }

  def allStateIdsAsMapForQuery() = {
    val statement =
      sql"""SELECT id, value FROM state""".stripMargin
    statement.query[(String, String)]
  }

  def answersQuery(query: String) = {
    val statement =
      sql"""SELECT * FROM answers WHERE title LIKE $query""".stripMargin
    statement.query[Answer]
  }

}
