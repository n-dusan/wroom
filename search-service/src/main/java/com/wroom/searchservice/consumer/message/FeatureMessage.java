package com.wroom.searchservice.consumer.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class FeatureMessage {

    private Long id;
    private String name;
    private Long brandId;

    public FeatureMessage() {}


    public FeatureMessage(@JsonProperty("id") Long id,
                          @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    @JsonCreator
    public FeatureMessage(@JsonProperty("id") Long id,
                          @JsonProperty("name") String name,
                          @JsonProperty("brandId") Long brandId) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }


}