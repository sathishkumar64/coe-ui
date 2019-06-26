package com.coeui.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class School implements Serializable {

	private static final long serialVersionUID = 1L;
	private String schoolId;
	private String schoolname;
	private String eduMode;
	private Address address;

	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getEduMode() {
		return eduMode;
	}

	public void setEduMode(String eduMode) {
		this.eduMode = eduMode;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
