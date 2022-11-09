package repo

import cats.effect.*
import cats.implicits.*

import io.circe.*

import doobie.implicits.*
import doobie.util.transactor.Transactor

import constants.Constants.*
import model.*

trait BotRepo:
  // Wiring up Repo Modules -- Transactor + Queries Class
  val xa = Transactor.fromDriverManager[IO](driver, DB_URL, dbUserName, dbPassword)
  val botQueries = new BotQueries(xa)

  // Interface
  def getAllMessages(): IO[List[String]]
  def searchAnswers(query: String): IO[Answer]
  def searchFullAnswers(query: String): IO[FullAnswer]

object BotRepo extends BotRepo:
  def getAllMessages() = botQueries.getMessages
  def searchAnswers(query: String) = botQueries.getAnswers(query)
  def searchFullAnswers(query: String) = botQueries.getFullAnswers(query)
