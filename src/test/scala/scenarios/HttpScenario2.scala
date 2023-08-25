package scenarios

import core.precondition.PreconditionScript
import core.servicesClients.http.Http
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import org.slf4j.{Logger, LoggerFactory}

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration


class HttpScenario2(preconditionScript: PreconditionScript) {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val httpClient: Http = preconditionScript.httpClient

  val scn: ScenarioBuilder = scenario("#HTTP SCENARIO 1#").
    exec(
      exec {
        session =>
          val ipApp = preconditionScript.propUtils.getConsulProperties.getHosts.getApp
          logger.info("ipApp" , ipApp)
          session
            .set("doHtmlDocTime", 0L).set("htmlDocTimeCalculate", false)
            .set("doJsonTime", 0L).set("jsonTimeCalculate", false)
            .set("doImageTime", 0L).set("imageTimeCalculate", false)
      }.
        exec(
          forever() {
            exec(
              httpClient.htmlDoc,
              httpClient.json,
              httpClient.image,
              pause(Duration.create(1000, TimeUnit.MILLISECONDS))
            )
          }
        )
    )
}
