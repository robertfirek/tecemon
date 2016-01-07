package me.crowdmix.tecemon;

import com.jayway.jsonpath.JsonPath;
import me.crowdmix.tecemon.teamcity.Repository;
import me.crowdmix.tecemon.teamcity.infrastructure.Http;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Start {

    private static final Logger logger = LogManager.getLogger(Start.class);

    public static void main(String[] args) {
        String pathToConfigurationFile = args[0];

        Configuration configuration = new Configuration(pathToConfigurationFile);
        Http http = new Http(configuration);
        Repository repository = new Repository(configuration, http);


        JSONObject allBuilds = repository.allBuilds();
        logger.info(readFromPath(allBuilds, "$.count", "0"));

        for (Object o : ((JSONArray) readFromPath(allBuilds, "$.build", "0"))) {
            logger.info(((Map) o));
        }
    }

    private static Object readFromPath(Object configDocument, String jsonPath, String defaultValue) {
        try {
            return JsonPath.read(configDocument, jsonPath);
        } catch (com.jayway.jsonpath.PathNotFoundException e) {
            return defaultValue;
        }
    }

}
