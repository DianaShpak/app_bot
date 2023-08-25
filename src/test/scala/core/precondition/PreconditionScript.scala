package core.precondition

import core.servicesClients.http.Http
import core.utils.PropertiesUtils
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import org.slf4j.{Logger, LoggerFactory}

class PreconditionScript {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val propUtils = new PropertiesUtils(1)
  propUtils.updatePropertiesWithSystemVariables()
  propUtils.prepareConsul()

  private val GatlingCustomConfigFile = "gatling.conf"
  private val GatlingCustomConfigFileOverrideSystemProperty = "gatling.conf.file"

  val customConfigFile: String = sys.props.getOrElse(GatlingCustomConfigFileOverrideSystemProperty, GatlingCustomConfigFile)


  var httpClient: Http = new Http(propUtils)

  val httpProtocol: HttpProtocolBuilder = http
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")


}
