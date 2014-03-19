
package com.prokarma.fifa.models.soccer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "year",
    "type",
    "description",
    "startDate",
    "endDate",
    "seasonTypeId",
    "seasonTypes"
})
public class Season {

    @JsonProperty("year")
    private Integer year;
    @JsonProperty("type")
    private Integer type;
    @JsonProperty("description")
    private String description;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("seasonTypeId")
    private Integer seasonTypeId;
    @JsonProperty("seasonTypes")
    private List<SeasonType> seasonTypes = new ArrayList<SeasonType>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("year")
    public Integer getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(Integer year) {
        this.year = year;
    }

    @JsonProperty("type")
    public Integer getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
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

    @JsonProperty("seasonTypeId")
    public Integer getSeasonTypeId() {
        return seasonTypeId;
    }

    @JsonProperty("seasonTypeId")
    public void setSeasonTypeId(Integer seasonTypeId) {
        this.seasonTypeId = seasonTypeId;
    }

    @JsonProperty("seasonTypes")
    public List<SeasonType> getSeasonTypes() {
        return seasonTypes;
    }

    @JsonProperty("seasonTypes")
    public void setSeasonTypes(List<SeasonType> seasonTypes) {
        this.seasonTypes = seasonTypes;
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
