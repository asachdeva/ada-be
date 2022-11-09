package repo

import io.circe.*
import cats.effect.*
import cats.implicits.*
import constants.Constants.*
import doobie.implicits.*
import doobie.util.transactor.Transactor
import model.*

trait BotRepo:
  // Wiring up Repo Modules -- Transactor + Queries Class
  val xa = Transactor.fromDriverManager[IO](driver, DB_URL, dbUserName, dbPassword)
  val botQueries = new BotQueries(xa)

  // Interface 
  def getAllMessages(): IO[List[String]]
  def searchAnswers(searchTitle: String): IO[Answer]

object BotRepo extends BotRepo:
  def getAllMessages() = botQueries.getMessages
  def searchAnswers(searchTitle: String) = botQueries.getAnswers(searchTitle)
