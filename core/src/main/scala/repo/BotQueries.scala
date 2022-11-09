package repo

import cats.*
import cats.effect.*
import cats.implicits.*

import io.circe.*

import doobie.*
import doobie.implicits.*
import doobie.util.transactor.Transactor

import model.*

class BotQueries(transactor: Transactor[IO]) {

  def parseRawMessages(rawMessages: List[String], allStateIdsMap: Map[String, String]) = {
    def secondParse(raw: String, allStateIdsMap: Map[String, String]): String =
      raw match
        case s"$head{$stateId|}$tail" if allStateIdsMap.contains(stateId) =>
          head + allStateIdsMap(stateId) + tail
        case s"$head{$stateId|$default}$tail" =>
          head + default + tail

    rawMessages.map {
      case s"$head{$stateId|}$tail" if allStateIdsMap.contains(stateId) =>
        head + allStateIdsMap(stateId) + tail
      case s"$head{$stateId|$default}$tail" if allStateIdsMap.contains(stateId) =>
        val stem = allStateIdsMap(stateId)
        val maybe = head + stem + tail.replace(s"{$stateId|}", stem)
        if (maybe.contains("{")) secondParse(maybe, allStateIdsMap) else maybe
      case s"$head{$stateId|$default}$tail" =>
        val maybe = head + default + tail
        if (maybe.contains("{")) secondParse(maybe, allStateIdsMap) else maybe
    }
  }

  def getMessages: IO[List[String]] =
    for {
      allStateIdsMap <- BotQueries.allStateIdsAsMapForQuery().to[List].transact(transactor).map(_.toMap)
      rawMessageBodies <- BotQueries.allMessageBodiesForQuery().to[List].transact(transactor)
      parsedMessages = parseRawMessages(rawMessageBodies, allStateIdsMap)
    } yield parsedMessages

  def getAnswers(query: String): IO[Answer] =
    for {
      result <- BotQueries.answersQuery(query).option.transact(transactor)
    } yield result.getOrElse(Answer(0, ""))

  def getFullAnswers(query: String): IO[FullAnswer] =
    for {
      result <- BotQueries.fullAnswersQuery(query).option.transact(transactor)
    } yield result.getOrElse(FullAnswer(0, 0, "", ""))

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

  def fullAnswersQuery(query: String) = {
    val likeQuery = query + "%"
    val statement =
      fr"""SELECT a.id, a.title, b.id as blockId, b.content FROM answers a  
          LEFT JOIN blocks b ON b.answer_id = a.id
          WHERE a.title LIKE $likeQuery""".stripMargin
    statement.query[FullAnswer]
  }

  def answersQuery(query: String) = {
    val likeQuery = query + "%"
    val statement =
      sql"""SELECT * FROM answer WHERE title LIKE $likeQuery""".stripMargin
    statement.query[Answer]
  }

}
