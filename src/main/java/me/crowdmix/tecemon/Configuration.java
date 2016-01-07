package me.crowdmix.tecemon;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.jayway.jsonpath.Configuration.defaultConfiguration;

public class Configuration {

    public static final String DEFAULT_VALUE = "UNKNOWN";

    private static final Logger logger = LogManager.getLogger(Configuration.class);
    private static final String CONFIG_FILE_ENCODING = "UTF-8";

    private Object configDocument;

    public Configuration(String pathToConfigFile) {
        try {
            FileInputStream configFileInputStream = new FileInputStream(pathToConfigFile);
            configDocument = defaultConfiguration().jsonProvider().parse(configFileInputStream, CONFIG_FILE_ENCODING);
        } catch (FileNotFoundException | NullPointerException e) {
            logger.info("Configuration file \"" + pathToConfigFile + "\" not found. Setup default values.");
            logger.debug("Configuration file \"" + pathToConfigFile + "\" not found. Setup default values.", e);
            configDocument = new JSONObject();
        }
    }

    public String getTeamCityUrl() {
        return readFromPath(configDocument, "$.TeamCity.url", DEFAULT_VALUE);
    }

    public String getTeamCityUser() {
        return readFromPath(configDocument, "$.TeamCity.credentials.user", DEFAULT_VALUE);
    }

    public String getTeamCityPassword() {
        return readFromPath(configDocument, "$.TeamCity.credentials.password", DEFAULT_VALUE);
    }

    private String readFromPath(Object configDocument, String jsonPath, String defaultValue) {
        try {
            return JsonPath.read(configDocument, jsonPath);
        } catch (com.jayway.jsonpath.PathNotFoundException e) {
            return defaultValue;
        }
    }
}
