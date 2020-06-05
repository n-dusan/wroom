package com.wroom.adsservice.producer.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationMessage {

	private Long id;
	private String country;
	private String city;
	
	public LocationMessage() {}
	
	public LocationMessage(@JsonProperty("id") Long id, 
			@JsonProperty("country") String country, 
			@JsonProperty("city") String city) {
		this.id = id;
		this.country = country;
		this.city = city;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}
