package scenarios

import core.precondition.PreconditionScript
import core.servicesClients.http.Http
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import org.slf4j.{Logger, LoggerFactory}

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration


class HttpScenario1(preconditionScript: PreconditionScript) {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val httpClient: Http = preconditionScript.httpClient

  val scn: ScenarioBuilder = scenario("#HTTP SCENARIO 1#").
    exec(
      exec {
        session =>
          val ipApp = preconditionScript.propUtils.getConsulProperties.getHosts.getApp
          logger.info("ipApp" , ipApp)
          session
            .set("doGetTime", 0L).set("getTimeCalculate", false)
            .set("doDeflateTime", 0L).set("deflateTimeCalculate", false)
            .set("doDenyTime", 0L).set("denyTimeCalculate", false)
            .set("doEncodingUtf8Time", 0L).set("encodingUtf8TimeCalculate", false)
      }.
      exec(
        forever() {
          exec(
            httpClient.get,
            httpClient.deflate,
            httpClient.deny,
            httpClient.encodingUtf8,
            pause(Duration.create(1000, TimeUnit.MILLISECONDS))
          )
        }
      )
    )
}
