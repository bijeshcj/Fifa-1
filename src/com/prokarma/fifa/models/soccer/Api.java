
package com.prokarma.fifa.models.soccer;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "leagues",
    "news",
    "notes",
    "headlines",
    "events",
    "teams",
    "athletes",
    "standings"
})
public class Api {

    @JsonProperty("leagues")
    private Leagues leagues;
    @JsonProperty("news")
    private News news;
    @JsonProperty("notes")
    private Notes notes;
    @JsonProperty("headlines")
    private Headlines headlines;
    @JsonProperty("events")
    private Events events;
    @JsonProperty("teams")
    private Teams teams;
    @JsonProperty("athletes")
    private Athletes athletes;
    @JsonProperty("standings")
    private Standings standings;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("leagues")
    public Leagues getLeagues() {
        return leagues;
    }

    @JsonProperty("leagues")
    public void setLeagues(Leagues leagues) {
        this.leagues = leagues;
    }

    @JsonProperty("news")
    public News getNews() {
        return news;
    }

    @JsonProperty("news")
    public void setNews(News news) {
        this.news = news;
    }

    @JsonProperty("notes")
    public Notes getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    @JsonProperty("headlines")
    public Headlines getHeadlines() {
        return headlines;
    }

    @JsonProperty("headlines")
    public void setHeadlines(Headlines headlines) {
        this.headlines = headlines;
    }

    @JsonProperty("events")
    public Events getEvents() {
        return events;
    }

    @JsonProperty("events")
    public void setEvents(Events events) {
        this.events = events;
    }

    @JsonProperty("teams")
    public Teams getTeams() {
        return teams;
    }

    @JsonProperty("teams")
    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    @JsonProperty("athletes")
    public Athletes getAthletes() {
        return athletes;
    }

    @JsonProperty("athletes")
    public void setAthletes(Athletes athletes) {
        this.athletes = athletes;
    }

    @JsonProperty("standings")
    public Standings getStandings() {
        return standings;
    }

    @JsonProperty("standings")
    public void setStandings(Standings standings) {
        this.standings = standings;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
