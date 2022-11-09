package api

import cats.effect.*
import constants.Constants.*
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.slf4j.Slf4jLogger

class BotServer(botService: BotService):
  private lazy val log4CatsLogger = Slf4jLogger.getLogger[IO]

  def apply(): IO[ExitCode] = for {
    _ <- log4CatsLogger.info("starting server")

    rc <- EmberServerBuilder
      .default[IO]
      .withHost(HOST_IP)
      .withPort(PORT)
      .withHttpApp(botService.routes)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  } yield rc
