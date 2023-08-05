package simulation

import core.constans.ScenariosName
import core.precondition.PreconditionScript
import io.gatling.core.Predef._
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.controller.inject.open.OpenInjectionStep
import io.gatling.http.protocol.HttpProtocolBuilder
import org.slf4j.{Logger, LoggerFactory}
import scenarios.{GameScenario, Test2}

import scala.concurrent.duration.DurationInt

class BasicSimulation extends Simulation {
  GatlingConfiguration.loadForTest()
  GatlingConfiguration.loadActorSystemConfiguration()

  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val preconditionScript: PreconditionScript = new PreconditionScript
  val basicSimulationUtils: BasicSimulationUtils = new BasicSimulationUtils(preconditionScript)
  val httpProtocol: HttpProtocolBuilder = preconditionScript.httpProtocol
  val defaultHeavySideUsers: OpenInjectionStep = basicSimulationUtils.defaultHeavySideUsers()

  preconditionScript.propUtils.getConsulProperties.getOther.getScenario match {
    case ScenariosName.TEST2 =>
      setUp(
        new Test2(preconditionScript).test.inject(heavisideUsers(1).during(60.seconds))).protocols(httpProtocol)

    case ScenariosName.SPIN =>
      setUp(
        new GameScenario(preconditionScript).test.inject(defaultHeavySideUsers)).protocols(httpProtocol)

    case _ =>
      throw new Exception("WRONG SCENARIO NAME")
  }
}
