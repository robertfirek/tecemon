package me.crowdmix.tecemon.teamcity;

import me.crowdmix.tecemon.Configuration;
import me.crowdmix.tecemon.teamcity.infrastructure.Http;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class RepositoryShould {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private Configuration configuration;

    @Mock
    private Http http;

    private String allProjectsJsonString = "{\"allProjects\":1}";
    private String allBuildsJsonString = "{\"allBuilds\":[]}";

    private Repository repository;

    @Before
    public void initialise() throws IOException {
        String teamCityUrl = "http://localhost";
        String teamCityUser = "TCUser";
        String teamCityPassword = "TCPass";

        when(configuration.getTeamCityUrl()).thenReturn(teamCityUrl);
        when(configuration.getTeamCityUser()).thenReturn(teamCityUser);
        when(configuration.getTeamCityPassword()).thenReturn(teamCityPassword);

        when(http.get(teamCityUrl + Repository.ALL_PROJECTS_URI)).thenReturn(allProjectsJsonString);
        when(http.get(teamCityUrl + Repository.ALL_BUILDS_URI)).thenReturn(allBuildsJsonString);
        repository = new Repository(configuration, http);
    }

    @Test
    public void
    returnAllProjectsFromTeamcity() {
        JSONObject projectsAsJsonString = repository.allProjects();
        assertThat(projectsAsJsonString.toJSONString(), is(allProjectsJsonString));
    }

    @Test
    public void
    returnAllBuildsFromTeamcity() {
        JSONObject projectsAsJsonString = repository.allBuilds();
        assertThat(projectsAsJsonString.toJSONString(), is(allBuildsJsonString));
    }
}
