package it.dosti.justit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
    private final double latitude;
    private final double longitude;


    @JsonCreator
    public Coordinates(@JsonProperty("latitude")  double latitude,@JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
