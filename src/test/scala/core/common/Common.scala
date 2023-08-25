package core.common

import org.slf4j.{Logger, LoggerFactory}
import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import scala.util.Random

object Common {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val DIGITS_LOWER: Array[Char] = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
  val chars: Array[Char] = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
  val randomMain: Random = new Random()

  def getRandomString(length: Int): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(chars(randomMain.nextInt(chars.length)))
    }
    sb.toString
  }

  def getSystemEnv(nm: String, defaultVal: String): String = {
    var v: String = null
    try {
      v = System.getenv(nm)
      if (v != null) return v
    }
    catch {
      case e@(_: IllegalArgumentException | _: NullPointerException | _: SecurityException) =>
    }
    return defaultVal
  }


}
