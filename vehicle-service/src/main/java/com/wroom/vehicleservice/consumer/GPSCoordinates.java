package com.wroom.vehicleservice.consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class GPSCoordinates {

    private String token;
    private String latitude;
    private String longitude;

    @JsonCreator
    public GPSCoordinates(@JsonProperty("token") String token,
                          @JsonProperty("latitude") String latitude,
                          @JsonProperty("longitude") String longitude) {
        this.token = token;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
