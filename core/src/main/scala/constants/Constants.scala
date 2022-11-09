package constants

import com.comcast.ip4s.*

object Constants:
  // Http Config
  val HOST_IP = ipv4"0.0.0.0"
  val PORT = port"5005"

  // Database Config
  val driver = "org.sqlite.JDBC"
  val dbName = "database"
  val dbUserName = ""
  val dbPassword = ""

  val DB_URL = s"jdbc:sqlite:$dbName.db"
