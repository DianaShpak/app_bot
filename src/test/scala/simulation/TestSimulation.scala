package simulation

import io.gatling.core.Predef._
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder
import org.slf4j.{Logger, LoggerFactory}
import scenarios._

import scala.concurrent.duration.DurationInt

class TestSimulation extends Simulation {
  GatlingConfiguration.loadForTest()
  GatlingConfiguration.loadActorSystemConfiguration()

  val logger: Logger = LoggerFactory.getLogger(classOf[TestSimulation])
  //  val preconditionScript: PreconditionScript = new PreconditionScript
  //  val properties: Properties = preconditionScript.properties

  //  val gameSimulation: GameScenario = new GameScenario(preconditionScript)
  //  val miniLobbyScenario: MiniLobbyScenario = new MiniLobbyScenario(preconditionScript)
  //  val test2: Test2 = new Test2(preconditionScript)
  //  val promoManagerScenario: PromoManagerScenario = new PromoManagerScenario(preconditionScript)
  //  val frbSimulation: FrbScenario = new FrbScenario(preconditionScript)

  val test1: Test3 = new Test3()

  val httpProtocol: HttpProtocolBuilder = http
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .disableFollowRedirect

  val test: ScenarioBuilder = scenario("#TestSimulation#")
    .exec(
      exec {
        session =>
          logger.debug("Test")
          session
      }
    )

  setUp(
    test.inject(heavisideUsers(1).during(10.seconds)),
  ).protocols(httpProtocol)
}
