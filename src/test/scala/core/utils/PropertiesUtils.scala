package core.utils

import core.common.Common
import core.constans.PropertiesFiles._
import core.exceptions.ConsulConnectionException
import core.managers.consul.ConsulManager
import core.managers.consul.model.Properties
import jodd.io.FileNameUtil
import org.apache.commons.collections4.IteratorUtils
import org.apache.commons.configuration.{ConfigurationException, PropertiesConfiguration}
import org.apache.commons.lang.StringUtils
import org.slf4j.LoggerFactory

import java.io._
import java.util
import java.util.concurrent.TimeUnit
import java.util.{Timer, TimerTask}
import scala.collection.mutable.ListBuffer

class PropertiesUtils extends TimerTask {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private var stagePropertiesFolder: String = _

  private val localRunStage: String = "diplom"

  private var properties: List[PropertiesConfiguration] = _

  private var consulProperties: Properties = _

  var consulManager: ConsulManager = _

  @throws[ConfigurationException]
  def this(any: Any) {
    this()
    val stageName: String = Common.getSystemEnv("propertiesFile", localRunStage).toLowerCase()

    stagePropertiesFolder = PROPERTIES_FOLDER_PATH + stageName

    if (!new File(stagePropertiesFolder).isDirectory) {
      logger.warn("Given path ( %s ) is not a folder. Trying to parse folder name...".format(stagePropertiesFolder))
      stagePropertiesFolder = stagePropertiesFolder.split(PROPERTIES_EXTENSION)(0)
    }

    logger.info("Properties will be taken from: " + stagePropertiesFolder)

    properties = getPropertyFiles.map(propertiesFile => new PropertiesConfiguration(propertiesFile))
  }

  @throws[ConfigurationException]
  def updatePropertiesWithSystemVariables(): Unit = {
    processProperties()
  }

  @throws[ConsulConnectionException]
  @throws[IOException]
  @throws[IllegalAccessException]
  def prepareConsul(): Unit = {
    val hostsProperties: PropertiesConfiguration = properties.filter(propertyFile =>
      propertyFile.getFileName.contains(HOSTS_PROPERTIES_FILE)).head

    val otherProperties: PropertiesConfiguration = properties.filter(propertyFile =>
      propertyFile.getFileName.contains(OTHER_PROPERTIES_FILE)).head

    val consulIP: String = hostsProperties.getString("consul", "non")

    consulManager = new ConsulManager(consulIP)

    if (consulManager.getKVValue("hosts/emul").isEmpty || otherProperties.getString("overwriteConsul") == "1") {
      logger.debug("Set properties to consul")
      setPropertiesToConsul()
    }

    consulProperties = consulManager.getProperties

    val timer = new Timer("Get consul props", true)

    timer.schedule(this, TimeUnit.MILLISECONDS.toMillis(10000), TimeUnit.MILLISECONDS.toMillis(10000))
  }


  def run(): Unit = {
    try {
      logger.trace("Try get consul props...")
      consulProperties = consulManager.getProperties
      logger.trace("Get consul props good )))")
    } catch {
      case e: Throwable =>
        logger.warn("PropertiesUtils update consul params fail!!! Exception: " + e.getClass.getName + "!!! Message: " + e.getMessage)
    }
  }

  @throws[IOException]
  def setPropertiesToConsul(): Unit = {
    for (propertiesFile <- properties) {
      setPropertiesFileToConsul(propertiesFile)
    }
  }

  @throws[IOException]
  private def setPropertiesFileToConsul(propertyFile: PropertiesConfiguration): Unit = {
    val consulKeyPrefix = consulManager.generateConsulKeyPrefix(propertyFile.getFileName)

    for (propertyName <- IteratorUtils.toList(propertyFile.getKeys).toArray) {
      var value = propertyFile.getProperty(propertyName.toString)

      if (value.isInstanceOf[util.ArrayList[String]]) {
        value = StringUtils.join(value.asInstanceOf[util.List[String]], ",")
      }

      logger.debug("\nkey: " + propertyName.toString + "\nvalue: " + value)

      consulManager
        .setKVValueString(
          consulKeyPrefix + propertyName.asInstanceOf[String], value.asInstanceOf[String])

    }
  }

  def getConsulProperties: Properties = consulProperties

  def getPropertyFiles: List[String] = {
    val commonDefaultProperties: List[String] =
      Utils.getFilesListInFolder(COMMON_DEFAULT_PROPERTIES_PATH).filter(file => file.endsWith(PROPERTIES_EXTENSION))

    if (!Utils.checkIfPathIsFolder(stagePropertiesFolder)) {
      logger.warn("Given path (%s) is not a folder. Trying to parse folder name...")
      stagePropertiesFolder = stagePropertiesFolder.split(PROPERTIES_EXTENSION)(0)
    }

    val stageProperties: List[String] =
      Utils.getFilesListInFolder(stagePropertiesFolder).filter(file => file.endsWith(PROPERTIES_EXTENSION))

    val readyPropertyFilesList = ListBuffer[String]()

    // check if specific property file is present in stage folder. if it does - use it.
    for (commonDefaultPropertyFile <- commonDefaultProperties) {
      var useCommonDefaultProperty: Boolean = true
      val commonDefaultPropertyFileName = FileNameUtil.removeExtension(FileNameUtil.getName(commonDefaultPropertyFile))

      for (stagePropertyFile <- stageProperties) {
        val stagePropertyFileName = FileNameUtil.removeExtension(FileNameUtil.getName(stagePropertyFile))

        if (stagePropertyFileName.equals(commonDefaultPropertyFileName)) {
          useCommonDefaultProperty = false
          readyPropertyFilesList.addOne(stagePropertyFile)
        }
      }

      if (useCommonDefaultProperty) {
        readyPropertyFilesList.addOne(commonDefaultPropertyFile)
      }
    }

    readyPropertyFilesList.toList
  }

  private def processProperties(): Unit = {

    val envVariables: util.Map[String, String] = System.getenv()
    val envsKeys = envVariables.keySet()

    // each env variable
    for (envKey <- envsKeys.toArray) {
      // each property file
      for (properties <- properties) {
        // each key in property file
        for (propertyFileKey <- IteratorUtils.toList(properties.getKeys).toArray) {
          // replace in properties with env key value
          if (envKey.equals(propertyFileKey)) {
            properties.setProperty(propertyFileKey.toString, envVariables.get(envKey))
            logger.debug("Found new value (%s) for key '%s' in env variables. Updating."
              .format(envVariables.get(envKey), envKey))
            properties.save()
          }
        }
      }
    }
  }
}
