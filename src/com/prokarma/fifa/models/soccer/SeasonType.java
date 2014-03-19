
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
    "id",
    "name",
    "abbreviation",
    "startDate",
    "endDate",
    "hasGroups",
    "hasStandings",
    "hasLegs"
})
public class SeasonType {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("abbreviation")
    private String abbreviation;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("hasGroups")
    private Boolean hasGroups;
    @JsonProperty("hasStandings")
    private Boolean hasStandings;
    @JsonProperty("hasLegs")
    private Boolean hasLegs;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("abbreviation")
    public String getAbbreviation() {
        return abbreviation;
    }

    @JsonProperty("abbreviation")
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("hasGroups")
    public Boolean getHasGroups() {
        return hasGroups;
    }

    @JsonProperty("hasGroups")
    public void setHasGroups(Boolean hasGroups) {
        this.hasGroups = hasGroups;
    }

    @JsonProperty("hasStandings")
    public Boolean getHasStandings() {
        return hasStandings;
    }

    @JsonProperty("hasStandings")
    public void setHasStandings(Boolean hasStandings) {
        this.hasStandings = hasStandings;
    }

    @JsonProperty("hasLegs")
    public Boolean getHasLegs() {
        return hasLegs;
    }

    @JsonProperty("hasLegs")
    public void setHasLegs(Boolean hasLegs) {
        this.hasLegs = hasLegs;
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
