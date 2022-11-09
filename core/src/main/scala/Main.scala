import cats.effect.*

import api.*
import model.*
import repo.*

object Main extends IOApp:
  def run(args: List[String]): IO[ExitCode] = {
    val botRepo = BotRepo
    val botService = BotService(botRepo)
    BotServer(botService).apply()
  }
