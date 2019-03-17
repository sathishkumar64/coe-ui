package com.coeui.model;

public class CountryInfo {
	
	private String location;
	private String flag;
	
	
	public CountryInfo(String location, String flag) {
		super();
		this.location = location;
		this.flag = flag;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	@Override
	public String toString() {
		return "CountryInfo [location=" + location + ", flag=" + flag + "]";
	}
	
	
	

}
