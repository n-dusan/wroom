package com.wroom.searchservice.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class VehicleMessage {

    private Long id;
    private Double mileage;
    private Integer childSeats;
    private Boolean cdw;

    private FeatureMessage modelType;
    private FeatureMessage brandType;
    private FeatureMessage bodyType;
    private FeatureMessage fuelType;
    private FeatureMessage gearboxType;

    private Long ownerId;

    public VehicleMessage() {}

    public VehicleMessage(@JsonProperty("id") Long id,
                          @JsonProperty("mileage") Double mileage,
                          @JsonProperty("childSeats") Integer childSeats,
                          @JsonProperty("cdw") Boolean cdw,
                          @JsonProperty("modelType") FeatureMessage modelType,
                          @JsonProperty("brandType") FeatureMessage brandType,
                          @JsonProperty("bodyType") FeatureMessage bodyType,
                          @JsonProperty("fuelType") FeatureMessage fuelType,
                          @JsonProperty("gearboxType") FeatureMessage gearboxType) {
        this.id = id;
        this.mileage = mileage;
        this.childSeats = childSeats;
        this.cdw = cdw;
        this.brandType = brandType;
        this.modelType = modelType;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.gearboxType = gearboxType;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Integer getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(Integer childSeats) {
        this.childSeats = childSeats;
    }

    public Boolean getCdw() {
        return cdw;
    }

    public void setCdw(Boolean cdw) {
        this.cdw = cdw;
    }

    public FeatureMessage getModelType() {
        return modelType;
    }

    public void setModelType(FeatureMessage modelType) {
        this.modelType = modelType;
    }

    public FeatureMessage getBrandType() {
        return brandType;
    }

    public void setBrandType(FeatureMessage brandType) {
        this.brandType = brandType;
    }

    public FeatureMessage getBodyType() {
        return bodyType;
    }

    public void setBodyType(FeatureMessage bodyType) {
        this.bodyType = bodyType;
    }

    public FeatureMessage getFuelType() {
        return fuelType;
    }

    public void setFuelType(FeatureMessage fuelType) {
        this.fuelType = fuelType;
    }

    public FeatureMessage getGearboxType() {
        return gearboxType;
    }

    public void setGearboxType(FeatureMessage gearboxType) {
        this.gearboxType = gearboxType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
