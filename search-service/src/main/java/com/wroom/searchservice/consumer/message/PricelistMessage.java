package com.wroom.searchservice.consumer.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PricelistMessage {

	private Long id;
	private Double pricePerDay;
	private Double pricePerMile;
	private Double priceCDW;
	private Double discount;
	private Long userId;
	
	public PricelistMessage() {}
	
	public PricelistMessage( @JsonProperty("id") Long id, 
			@JsonProperty("pricePerDay") Double pricePerDay,
			@JsonProperty("pricePerMile") Double pricePerMile,
			@JsonProperty("priceCDW") Double priceCDW, 
			@JsonProperty("discount") Double discount, 
			@JsonProperty("userId") Long userId) {
		super();
		this.id = id;
		this.pricePerDay = pricePerDay;
		this.pricePerMile = pricePerMile;
		this.priceCDW = priceCDW;
		this.discount = discount;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Double getPriceCDW() {
		return priceCDW;
	}

	public void setPriceCDW(Double priceCDW) {
		this.priceCDW = priceCDW;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getPricePerMile() {
		return pricePerMile;
	}

	public void setPricePerMile(Double pricePerMile) {
		this.pricePerMile = pricePerMile;
	}

	
}
