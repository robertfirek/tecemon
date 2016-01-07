package me.crowdmix.tecemon.teamcity;

public class TeamCityException extends RuntimeException {
    public TeamCityException(String message, Throwable cause) {
        super(message, cause);
    }
}
