package simulation

import core.precondition.PreconditionScript
import io.gatling.core.Predef.{DurationInteger, heavisideUsers}
import io.gatling.core.controller.inject.open.OpenInjectionStep
import org.slf4j.{Logger, LoggerFactory}

class BasicSimulationUtils(preconditionScript: PreconditionScript) {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def defaultHeavySideUsers(): OpenInjectionStep = {
    heavisideUsers(
      preconditionScript.propUtils.getConsulProperties.getOther.getUsersPerContainer)
      .during(preconditionScript.propUtils.getConsulProperties.getOther.getDuring.seconds)
  }

}
