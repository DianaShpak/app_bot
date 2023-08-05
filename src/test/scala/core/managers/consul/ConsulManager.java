package core.managers.consul;

import com.ecwid.consul.v1.ConsulClient;
import core.exceptions.ConsulConnectionException;
import core.managers.consul.model.*;
import jodd.io.FileNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static core.constans.PropertiesFiles.TIMINGS_PROPERTIES_POSTFIX;

public class ConsulManager {
    private final ConsulClient client;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ConsulManager(String agentHost) throws ConsulConnectionException {
        client = new ConsulClient(agentHost);
        checkClient(client);
    }

    private void checkClient(ConsulClient client) throws ConsulConnectionException {
        try {
            client.getAgentChecks();
        } catch (Exception ex) {
            throw new ConsulConnectionException("Error while connecting to consul. Check the consul's ip address.");
        }
    }

    public void setKVValueString(String key, String value) {
        client.setKVValue(key, value);
    }

    public String getKVValue(String key) {
        try {
            return client.getKVValue(key).getValue().getDecodedValue();
        } catch (NullPointerException ex) {
            return EMPTY_STRING;
        }
    }

    public Properties getProperties() throws IllegalAccessException {
        List<String> absentKeys = new ArrayList<>();

        Properties properties = new Properties();

        properties.setHosts(setValueInFields(new Hosts(), absentKeys));
        properties.setOther(setValueInFields(new Other(), absentKeys));
        properties.setTimings(getTimingsObject(absentKeys));

        if (absentKeys.size() > 0) {
            logger.error("Couldn't get values from consul for next keys:\n" + absentKeys);
            throw new NullPointerException();
        }

        return properties;
    }

    public String generateConsulKeyPrefix(String propertiesFileName) {
        String fileName = FileNameUtil.removeExtension(FileNameUtil.getName(propertiesFileName));

        if (fileName.contains(TIMINGS_PROPERTIES_POSTFIX)) {
            return "timings/" + fileName + "/";
        } else {
            return fileName + "/";
        }
    }

    private Timings getTimingsObject(List<String> absentKeys) throws IllegalAccessException {

        Timings timings = new Timings();
        String consulPathToGeneralTimingKeys = "timings/generalTimings/";

        for (Field field : timings.getClass().getDeclaredFields()) {

            if (field.getType().getName().startsWith("java.lang")) {
                setConsulValueForField(absentKeys, consulPathToGeneralTimingKeys, timings, field);
            } else {
                Object timingsSubObject;

                try {
                    timingsSubObject = Class.forName(field.getType().getName()).getConstructor().newInstance();

                } catch (InstantiationException | InvocationTargetException |
                        NoSuchMethodException | ClassNotFoundException e) {
                    logger.error(e.getMessage());
                    logger.error(e.getCause().getMessage());
                    e.printStackTrace();
                    throw new UnknownError("Read the error message");
                }

                setValueInFields(timingsSubObject, absentKeys);
                field.setAccessible(true);
                field.set(timings, timingsSubObject);
            }
        }

        return timings;

    }

    private <T> T setValueInFields(Object obj, List<String> listToCollectAbsentKeys) throws IllegalAccessException {
        T t = (T) obj;
        String consulPathToKey;

        Field[] fields = t.getClass().getDeclaredFields();
        String className = t.getClass().getSimpleName();
        className = Character.toLowerCase(className.charAt(0)) + className.substring(1);

        if (className.contains(TIMINGS_PROPERTIES_POSTFIX)) {
            consulPathToKey = "timings/" + className + "/";
        } else {
            consulPathToKey = className + "/";
        }

        for (Field field : fields) {
            setConsulValueForField(listToCollectAbsentKeys, consulPathToKey, t, field);
        }
        return t;
    }

    private <T> void setConsulValueForField(List<String> listToCollectAbsentKeys,
                                            String consulPathToKey, T objectToSet,
                                            Field field) throws IllegalAccessException {

        String key = consulPathToKey.concat(field.getName());
        String consulValue = getKVValue(key);

        if (consulValue.equals(EMPTY_STRING)) {
            listToCollectAbsentKeys.add(key);
            return;
        }

        field.setAccessible(true);
        Class<?> clazz = field.getType();
        if (clazz.equals(String.class)) {
            field.set(objectToSet, consulValue);
        } else if (clazz.equals(Long.class)) {
            field.set(objectToSet, Long.parseLong(consulValue));
        } else if (clazz.equals(Double.class)) {
            field.set(objectToSet, Double.parseDouble(consulValue));
        } else if (clazz.equals(Integer.class)) {
            field.set(objectToSet, Integer.parseInt(consulValue));
        } else if (clazz.equals(Boolean.class)) {
            field.set(objectToSet, Boolean.parseBoolean(consulValue));
        } else if (clazz.equals(List.class)) {
            field.set(objectToSet, Arrays.asList(consulValue.split(",")));
        }
    }
}
