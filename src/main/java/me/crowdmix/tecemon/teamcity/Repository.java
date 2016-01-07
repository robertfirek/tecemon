package me.crowdmix.tecemon.teamcity;

import me.crowdmix.tecemon.Configuration;
import me.crowdmix.tecemon.teamcity.infrastructure.Http;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Repository {

    private static final Logger logger = LogManager.getLogger(Repository.class);

    public static final String ALL_PROJECTS_URI = "/app/rest/projects";
    public static final String ALL_BUILDS_URI = "/app/rest/builds/?locator=count:100000,branch:(default:true),running:(any),affectedProject:(id:_Root),running:(any),sinceBuild:(id:0)";

    private final Configuration configuration;
    private final Http http;

    public Repository(Configuration configuration, Http http) {
        this.configuration = configuration;
        this.http = http;
    }

    public JSONObject allProjects() {
        return requestTeamCityUri(ALL_PROJECTS_URI);
    }

    public JSONObject allBuilds() {
        return requestTeamCityUri(ALL_BUILDS_URI);
    }

    private JSONObject requestTeamCityUri(String uri) {
        try {
            return parseJsonString(http.get(configuration.getTeamCityUrl() + uri));
        } catch (IOException e) {
            throwTeamCityException("Connection to \"" + configuration.getTeamCityUrl() + uri + "\" failed.", e);
        } catch (ParseException e) {
            throwTeamCityException("Wrong response format. Requires JSON format.", e);
        }
        return new JSONObject();
    }

    private JSONObject parseJsonString(String jsonAsString) throws ParseException {
        return new JSONParser(JSONParser.MODE_PERMISSIVE).parse(jsonAsString, JSONObject.class);
    }

    private JSONObject throwTeamCityException(String errorMessage, Exception e) {
        logger.debug(errorMessage, e);
        throw new TeamCityException(errorMessage, e);
    }
}
