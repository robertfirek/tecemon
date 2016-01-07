package me.crowdmix.tecemon;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfigurationShould {
    @Test public void
    readTeamCityConfigurationFromConfigFile() {
        String expectedUserName = "TestUser";
        String expectedUserPassword = "TestPassword";
        String expectedUrl = "http://teamcity.url";
        String pathToConfigFile = getClass().getResource("/config.json").getPath();

        Configuration configuration = new Configuration(pathToConfigFile);

        assertThat(configuration.getTeamCityUser(), is(expectedUserName));
        assertThat(configuration.getTeamCityPassword(), is(expectedUserPassword));
        assertThat(configuration.getTeamCityUrl(), is(expectedUrl));
    }

    @Test public void
    returnDefaultValuesWhenConfigFileIsNotAvailable() {
        Configuration configuration = new Configuration(null);

        assertThat(configuration.getTeamCityUser(), is(Configuration.DEFAULT_VALUE));
        assertThat(configuration.getTeamCityPassword(), is(Configuration.DEFAULT_VALUE));
        assertThat(configuration.getTeamCityUrl(), is(Configuration.DEFAULT_VALUE));
    }
}
