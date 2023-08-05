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


  def calcHmacMd5AndBase64Hash(source: String, secretKey: String): String = {
    var resultHash: String = "hash_fail"

    val signingKey = new SecretKeySpec(secretKey.getBytes, "HmacMD5")
    val mac = Mac.getInstance("HmacMD5")
    mac.init(signingKey)
    var macDoFinal: Array[Byte] = mac.doFinal(source.getBytes(StandardCharsets.UTF_8))

    val encodeHexString: String = new String(encodeHex(macDoFinal))

    logger.debug("encodeHexString: " + encodeHexString)

    //Base64 Encoding
    resultHash = Base64.getEncoder.encodeToString(encodeHexString.getBytes)

    logger.debug("resultHash: " + resultHash)
    return resultHash
  }

  def encodeHex(data: Array[Byte]): Array[Char] = {
    val l = data.length
    val out = new Array[Char](l << 1)
    // two characters form the hex value.
    var i = 0
    var j = 0
    while ( {
      i < l
    }) {
      out({
        j += 1;
        j - 1
      }) = DIGITS_LOWER((0xF0 & data(i)) >>> 4)
      out({
        j += 1;
        j - 1
      }) = DIGITS_LOWER(0x0F & data(i))

      i += 1
    }
    return out
  }

  def getRandomString(length: Int): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(chars(randomMain.nextInt(chars.length)))
    }
    sb.toString
  }

  def validateResponse(response: String, respStatus: Integer): Boolean = {
    if (respStatus > 200
      || response.contains("frozen")
      || response.contains("unlogged")
      || response.contains("invalid")
      || response.contains("Internal")
      || response.contains("\"error\":true,")
      || (response.contains("\"error\":") && !response.contains("\"error\":0,") && !response.contains("\"error\":\"0\",") && !response.contains("\"error\":false,"))
    ) {
      false
    } else {
      true
    }
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

  def getAuthenticationStr(username: String, secretKey: String, message: String): String = {
    import javax.crypto.spec.SecretKeySpec
    val key = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacMD5")
    import javax.crypto.Mac
    val mac = Mac.getInstance("HmacMD5")
    mac.init(key)
    val bytes = mac.doFinal(message.getBytes("ASCII"))
    val hash = new StringBuffer
    var i = 0
    while ( {
      i < bytes.length
    }) {
      val hex = Integer.toHexString(0xFF & bytes(i))
      if (hex.length == 1) hash.append('0')
      hash.append(hex)

      i += 1
    }
    val digest = hash.toString
    val bytesEncoded = java.util.Base64.getEncoder.encode(digest.getBytes())
    return "hmac " + username + ":" + new String(bytesEncoded)
  }

}
