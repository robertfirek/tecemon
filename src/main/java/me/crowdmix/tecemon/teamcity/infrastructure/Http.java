package me.crowdmix.tecemon.teamcity.infrastructure;

import me.crowdmix.tecemon.Configuration;
import org.apache.http.HttpHeaders;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class Http {
    private final String encodedCredentials;

    public Http(Configuration configuration) {
        String credentials = configuration.getTeamCityUser() + ":" + configuration.getTeamCityPassword();
        encodedCredentials = encodeBase64String(credentials.getBytes());

    }

    public String get(String nodeLocation) throws IOException {
        return Request.Get(nodeLocation)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .addHeader(HttpHeaders.ACCEPT, "application/json")
                .addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                .execute()
                .returnContent()
                .asString();
    }
}
