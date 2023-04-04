package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class Planet {

    private String climate;
    private String diameter;
    private String gravity;
    private String name;
    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    private String population;
    private List<String> residents;
    @JsonProperty("rotation_period")
    private String rotationPeriod;
    @JsonProperty("surface_water")
    private String surfaceWater;
    private String terrain;
    private String url;

    public String getName() {
        return name;
    }

    public List<String> getResidents() {
        return residents;
    }
}
