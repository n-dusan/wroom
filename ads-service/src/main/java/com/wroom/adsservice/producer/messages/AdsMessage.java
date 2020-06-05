package com.wroom.adsservice.producer.messages;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class AdsMessage {

	private Long id;
	private Date publishDate;
	private Date availableFrom;
	private Date availableTo;
	private Double mileLimit;
	private boolean mileLimitEnabled;
	private boolean gps;
	private String address;
	private Long vehicleId;
	
	private PricelistMessage priceList;
	private LocationMessage location;
	
    private OperationEnum operation;
    private EntityEnum entity;

    public AdsMessage() {}

    @JsonCreator
	public AdsMessage(@JsonProperty("id") Long id,
			@JsonProperty("publishDate") Date publishDate,
			@JsonProperty("availableFrom") Date availableFrom,
			@JsonProperty("availableTo") Date availableTo,
			@JsonProperty("mileLimit") Double mileLimit,
			@JsonProperty("mileLimitEnabled") boolean mileLimitEnabled, 
			@JsonProperty("gps") boolean gps, 
			@JsonProperty("address") String address,
			@JsonProperty("vehicleId") Long vehicleId, 
			@JsonProperty("priceList") PricelistMessage priceList,
			@JsonProperty("location") LocationMessage location, 
			@JsonProperty("operation") OperationEnum operation, 
			@JsonProperty("entity") EntityEnum entity) {
		super();
		this.id = id;
		this.publishDate = publishDate;
		this.availableFrom = availableFrom;
		this.availableTo = availableTo;
		this.mileLimit = mileLimit;
		this.mileLimitEnabled = mileLimitEnabled;
		this.gps = gps;
		this.address = address;
		this.vehicleId = vehicleId;
		this.priceList = priceList;
		this.location = location;
		this.operation = operation;
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}

	public Date getAvailableTo() {
		return availableTo;
	}

	public void setAvailableTo(Date availableTo) {
		this.availableTo = availableTo;
	}

	public Double getMileLimit() {
		return mileLimit;
	}

	public void setMileLimit(Double mileLimit) {
		this.mileLimit = mileLimit;
	}

	public boolean isMileLimitEnabled() {
		return mileLimitEnabled;
	}

	public void setMileLimitEnabled(boolean mileLimitEnabled) {
		this.mileLimitEnabled = mileLimitEnabled;
	}

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public PricelistMessage getPriceList() {
		return priceList;
	}

	public void setPriceList(PricelistMessage priceList) {
		this.priceList = priceList;
	}

	public LocationMessage getLocation() {
		return location;
	}

	public void setLocation(LocationMessage location) {
		this.location = location;
	}

	public OperationEnum getOperation() {
		return operation;
	}

	public void setOperation(OperationEnum operation) {
		this.operation = operation;
	}

	public EntityEnum getEntity() {
		return entity;
	}

	public void setEntity(EntityEnum entity) {
		this.entity = entity;
	}

}
