package core.servicesClients.http

import core.utils.{PropertiesUtils, Utils}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.slf4j.{Logger, LoggerFactory}

import java.time.Instant

class HttpTestClient(val propertiesUtils: PropertiesUtils) {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val GET: String = "/get"
  val DEFLATE: String = "/deflate"
  val DENY: String = "/deny"
  val ENCODING_UTF8: String = "/encoding/utf8"
  val HTML_DOC: String = "/html"
  val JSON: String = "/json"
  val IMAGE: String = "/image"


  val get: ChainBuilder = exec(
    doIf(session => session("getTimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doGetTime").as[Long]
          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getGetDelaySecondMin.longValue()
          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getGetDelaySecondMax.longValue()
          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
          val time = stateTime + randomTime
          session.set("doGetTime", time).set("getTimeCalculate", false)
      }
    },
    doIf(session => session("doGetTime").as[Long] <= (Instant.now().toEpochMilli / 1000)) {
      exec(
        http("GET")
          .get(session => "http://"  + propertiesUtils.getConsulProperties.getHosts.getApp + GET)
          .check(status.is(200))
          .check(bodyString.saveAs("getResponse"))
      ).exec { session =>
        val response: String = session("getResponse").as[String]
        logger.info("getResponse - " + response)
        session
          .set("doGetTime", Instant.now().toEpochMilli / 1000)
          .set("getTimeCalculate", true)
      }
    }
  )

  val deflate: ChainBuilder = exec(
    doIf(session => session("deflateTimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doDeflateTime").as[Long]
          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getDeflateDelaySecondMin.longValue()
          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getDeflateDelaySecondMax.longValue()
          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
          val time = stateTime + randomTime
          session.set("doDeflateTime", time).set("deflateTimeCalculate", false)
      }
    },
    doIf(session => session("doDeflateTime").as[Long] <= (Instant.now().toEpochMilli / 1000)) {
      exec(
        http("DEFLATE")
          .get(session => "http://" + propertiesUtils.getConsulProperties.getHosts.getApp + DEFLATE)
          .check(status.is(200))
          .check(bodyString.saveAs("deflateResponse"))
      ).exec { session =>
        val response: String = session("deflateResponse").as[String]
        logger.info("deflateResponse - " + response)
        session
          .set("doDeflateTime", Instant.now().toEpochMilli / 1000)
          .set("deflateTimeCalculate", true)
      }
    }
  )

  val deny: ChainBuilder = exec(
    doIf(session => session("denyTimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doDenyTime").as[Long]
          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getDenyDelaySecondMin.longValue()
          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getDenyDelaySecondMax.longValue()
          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
          val time = stateTime + randomTime
          session.set("doDenyTime", time).set("denyTimeCalculate", false)
      }
    },
    doIf(session => session("doDenyTime").as[Long] <= (Instant.now().toEpochMilli / 1000)) {
      exec(
        http("DENY")
          .get(session => "http://" + propertiesUtils.getConsulProperties.getHosts.getApp + DENY)
          .check(status.is(200))
          .check(bodyString.saveAs("denyResponse"))
      ).exec { session =>
        val response: String = session("denyResponse").as[String]
        logger.info("denyResponse - " + response)
        session
          .set("doDenyTime", Instant.now().toEpochMilli / 1000)
          .set("denyTimeCalculate", true)
      }
    }
  )

  val encodingUtf8: ChainBuilder = exec(
    doIf(session => session("encodingUtf8TimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doEncodingUtf8Time").as[Long]
          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getEncodingUtf8DelaySecondMin.longValue()
          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getEncodingUtf8DelaySecondMax.longValue()
          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
          val time = stateTime + randomTime
          session.set("doEncodingUtf8Time", time).set("encodingUtf8TimeCalculate", false)
      }
    },
    doIf(session => session("doEncodingUtf8Time").as[Long] <= (Instant.now().toEpochMilli / 1000)) {
      exec(
        http("ENCODING_UTF8")
          .get(session => "http://" + propertiesUtils.getConsulProperties.getHosts.getApp + ENCODING_UTF8)
          .check(status.is(200))
          .check(bodyString.saveAs("encodingUtf8Response"))
      ).exec { session =>
        val response: String = session("encodingUtf8Response").as[String]
        logger.info("encodingUtf8Response - " + response)
        session
          .set("doEncodingUtf8Time", Instant.now().toEpochMilli / 1000)
          .set("encodingUtf8TimeCalculate", true)
      }
    }
  )

  val htmlDoc: ChainBuilder = exec(
    doIf(session => session("htmlDocTimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doHtmlDocTime").as[Long]
          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getHtmlDocDelaySecondMin.longValue()
          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getHtmlDocDelaySecondMax.longValue()
          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
          val time = stateTime + randomTime
          session.set("doHtmlDocTime", time).set("htmlDocTimeCalculate", false)
      }
    },
    doIf(session => session("doHtmlDocTime").as[Long] <= (Instant.now().toEpochMilli / 1000)) {
      exec(
        http("HTML_DOC")
          .get(session => "http://" + propertiesUtils.getConsulProperties.getHosts.getApp + HTML_DOC)
          .check(status.is(200))
          .check(bodyString.saveAs("htmlDocResponse"))
      ).exec { session =>
        val response: String = session("htmlDocResponse").as[String]
        logger.info("htmlDocResponse - " + response)
        session
          .set("doHtmlDocTime", Instant.now().toEpochMilli / 1000)
          .set("htmlDocTimeCalculate", true)
      }
    }
  )

  val json: ChainBuilder = exec(
    doIf(session => session("jsonTimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doJsonTime").as[Long]
          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getJsonDelaySecondMin.longValue()
          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getJsonDelaySecondMax.longValue()
          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
          val time = stateTime + randomTime
          session.set("doJsonTime", time).set("jsonTimeCalculate", false)
      }
    },
    doIf(session => session("doJsonTime").as[Long] <= (Instant.now().toEpochMilli / 1000)) {
      exec(
        http("JSON")
          .get(session => "http://" + propertiesUtils.getConsulProperties.getHosts.getApp + JSON)
          .check(status.is(200))
          .check(bodyString.saveAs("jsonResponse"))
      ).exec { session =>
        val response: String = session("jsonResponse").as[String]
        logger.info("jsonResponse - " + response)
        session
          .set("doJsonTime", Instant.now().toEpochMilli / 1000)
          .set("jsonTimeCalculate", true)
      }
    }
  )

  val image: ChainBuilder = exec(
    doIf(session => session("imageTimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doImageTime").as[Long]
          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getImageDelaySecondMin.longValue()
          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getImageDelaySecondMax.longValue()
          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
          val time = stateTime + randomTime
          session.set("doImageTime", time).set("imageTimeCalculate", false)
      }
    },
    doIf(session => session("doImageTime").as[Long] <= (Instant.now().toEpochMilli / 1000)) {
      exec(
        http("IMAGE")
          .get(session => "http://" + propertiesUtils.getConsulProperties.getHosts.getApp + IMAGE)
          .check(status.is(406))
          .check(bodyString.saveAs("imageResponse"))
      ).exec { session =>
        val response: String = session("imageResponse").as[String]
        logger.info("imageResponse - " + response)
        session
          .set("doImageTime", Instant.now().toEpochMilli / 1000)
          .set("imageTimeCalculate", true)
      }
    }
  )
}
