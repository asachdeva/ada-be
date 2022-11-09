package repo

import cats.effect.IO
import cats.effect.unsafe.IORuntime

import doobie.implicits.*
import doobie.scalatest.IOChecker
import doobie.util.transactor.Transactor
import doobie.{Fragment, Transactor}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}

import constants.Constants.*

trait BotQueriesSpec extends AnyFlatSpec with Matchers with BeforeAndAfterAll with BeforeAndAfterEach with IOChecker {
  implicit val ioRuntime: IORuntime = cats.effect.unsafe.implicits.global
  implicit lazy val transactor: Transactor[IO] =
    Transactor.fromDriverManager[IO](driver, DB_URL, dbUserName, dbPassword)
}

class BotQueriesCheckerSpec extends BotQueriesSpec {
//  "allMessageBodies" should "check" in {
//    check(BotQueries.allMessageBodiesForQuery())
//  }
//  "allStates" should "check" in {
//    check(BotQueries.allStateIdsAsMapForQuery())
//  }
}
