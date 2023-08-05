package core.servicesClients.gs2c

import core.utils.{PropertiesUtils, Utils}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.slf4j.{Logger, LoggerFactory}

import java.time.Instant

class Gs2c(val propertiesUtils: PropertiesUtils) {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val GS2C_PROMO_RACE_DETAILS: String = "/gs2c/promo/race/details/"


  val raceDetails: ChainBuilder = exec(
    doIf(session => session("raceDetailsTimeCalculate").as[Boolean] == true) {
      exec {
        session =>
          val stateTime = session("doRaceDetailsTime").as[Long]
//          val delaySecondMin = propertiesUtils.getConsulProperties.getTimings.getRaceDetailsRandomDelaySecondMin.longValue()
//          val delaySecondMax = propertiesUtils.getConsulProperties.getTimings.getRaceDetailsRandomDelaySecondMax.longValue()
//          val randomTime = delaySecondMin + (Math.random * ((delaySecondMax - delaySecondMin) + 1)).asInstanceOf[Long]
//          val time = stateTime + randomTime
          session.set("doRaceDetailsTime", "time").set("raceDetailsTimeCalculate", false)
      }
    },
    doIf(session => session("doRaceDetailsTime").as[Long] <= (Instant.now().toEpochMilli / 1000) && session("isRaces").as[Boolean]) {
      exec(
        http("RaceDetails")
          .get(session => "propertiesUtils.getConsulProperties.getOther.getEmulHttpProtocol" + "://" + Utils.getDomainFromUrl(session("url").as[String]) + GS2C_PROMO_RACE_DETAILS)
          .queryParam("symbol", "${gameSymbol}")
//          .queryParam("mgckey", session => session(InnerVariableName.gameConfig).as[GameConfig].getMgckey)
          .check(status.is(200))
          .check(jsonPath("$.error").is("0"))
          .check(bodyString.saveAs("raceDetailsResponse"))
      ).exec { session =>
        val response: String = session("raceDetailsResponse").as[String]
        logger.info("raceDetailsResponse - " + response)
        session
          .set("doRaceDetailsTime", Instant.now().toEpochMilli / 1000)
          .set("raceDetailsTimeCalculate", true)
      }
    }
  )



}
