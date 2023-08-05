package scenarios

import core.precondition.PreconditionScript
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import org.slf4j.{Logger, LoggerFactory}

class Test2(preconditionScript: PreconditionScript) {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val test: ScenarioBuilder = scenario("#TEST2#")
    .exec(
      exec {
        session =>
          logger.debug("**************************************************")
          logger.debug("TEST2")
          logger.debug("TEST2")
          logger.debug("TEST2")
          logger.debug("TEST2")
          logger.debug(Thread.currentThread().getId.toString)
          logger.debug(Thread.currentThread().getId.toString)
          logger.debug(Thread.currentThread().getName.toString)
          logger.debug(Thread.currentThread().getName.toString)
          logger.debug("TEST2")
          logger.debug("TEST2")
          logger.debug("TEST2")
          logger.debug("**************************************************")
          session
            .set("stateMachineActionTimeCalculate", false)
            .set("doStateMachineActionTime", 0L)
      }
    ).
    exec {
      session =>
        //init parameters
        session
          .set("stateMachineActionTimeCalculate", false).set("doStateMachineActionTime", 0L)
          .set("openGameTimeCalculate", false).set("doOpenGameTime", 0L)
          .set("promoGetActiveTimeCalculate", false).set("doPromoGetActiveTime", 0L)
          .set("tournamentDetailsTimeCalculate", false).set("doTournamentDetailsTime", 0L)
          .set("raceDetailsTimeCalculate", false).set("doRaceDetailsTime", 0L)
          .set("miniLobbyTimeCalculate", false).set("doMiniLobbyTime", 0L)
    }.
    exec(
      exec {
        session =>
          logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug(System.getProperties.toString)
          logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
          logger.debug(session.hashCode().toString)
          logger.debug(session.hashCode().toString)
          logger.debug(session.hashCode().toString)
          logger.debug(session.hashCode().toString)
          logger.debug(session.hashCode().toString)
          logger.debug(session.hashCode().toString)

          session
      }
    )
}
