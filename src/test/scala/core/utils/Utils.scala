package core.utils

import org.apache.commons.lang.time.DateUtils
import org.slf4j.LoggerFactory

import java.io.{File, InputStream}
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import scala.util.Random.between

object Utils {

  private val logger = LoggerFactory.getLogger(this.getClass)

  val DUMMY_BYTE: Byte = 'A'.toByte

  def generateFakeInputStream(n: Int): InputStream = {
    require(n > 0)

    val dummyInputStream: InputStream = new InputStream {
      var counter: Int = 0

      def resetAndCloseStream: Int = {
        counter = 0
        -1 // end of stream
      }

      override def read(): Int = {
        counter += 1
        if (counter <= n) DUMMY_BYTE
        else resetAndCloseStream
      }
    }
    dummyInputStream
  }

  def getRandomString(length: Int, useLetters: Boolean, useNumbers: Boolean): String = {
    import org.apache.commons.lang.RandomStringUtils
    val generatedString = RandomStringUtils.random(length, useLetters, useNumbers)

    return generatedString
  }

  def getYesterdayDateInFormat(format: String): String = {
    val date = DateUtils.addDays(new Date, -1)
    val sdf = new SimpleDateFormat(format)
    return sdf.format(date)
  }

  def getPastYesterdayDateInFormat(format: String): String = {
    val date = DateUtils.addDays(new Date, -2)
    val sdf = new SimpleDateFormat(format)
    return sdf.format(date)
  }

  def getDateInFormatByTimeStamp(format: String, timeStamp: Long): String = {
    new SimpleDateFormat(format).format(timeStamp)
  }

  def getCurrentTimeSeconds: Long = {
    System.currentTimeMillis() / 1000
  }

  def generateRandomFutureTimestamp(minSecondsValue: Long, maxSecondsValue: Long): Long = {
    between(minSecondsValue, maxSecondsValue) + System.currentTimeMillis() / 1000
  }

  def generateFutureTimeStamp(futureSeconds: Long): Long = {
    System.currentTimeMillis() / 1000 + futureSeconds
  }

  def checkIfPathIsFolder(folderPath: String): Boolean = {
    new File(folderPath).isDirectory
  }

  def getFilesListInFolder(folderPath: String): List[String] = {
    val file = new File(folderPath)
    file.listFiles.filter(_.isFile)
      .map(_.getPath).toList
  }

  def getDomainFromUrl(url: String): String = {
    new URL(url).getHost
  }

}
