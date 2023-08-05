package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import org.slf4j.{Logger, LoggerFactory}


class Test3() {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val test: ScenarioBuilder = scenario("#TEST3#")
    .exec(
      exec {
        session =>
          logger.debug("**************************************************")
          logger.debug("TEST3")
          logger.debug("TEST3")
          logger.debug("TEST3")
          logger.debug("TEST3")
          logger.debug("TEST3")
          logger.debug("**************************************************")

          session.set("stateMachineActionTimeCalculate", false)
            .set("doStateMachineActionTime", 0L)
      }
    ).exec(
    exec {
      session =>
        logger.debug("**************************************************")
        logger.debug(session("stateMachineActionTimeCalculate").as[String])
        logger.debug(session("stateMachineActionTimeCalculate").as[String])
        logger.debug(session("stateMachineActionTimeCalculate").as[String])
        logger.debug(session("stateMachineActionTimeCalculate").as[String])
        logger.debug("**************************************************")

        session
    }
  )
}
