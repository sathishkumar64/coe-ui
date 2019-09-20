package com.coeui.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)	
public class SchoolAppData {

	private String schoolAppInfo;

	private String message;
	
	private String schoolName;

	private List<School> listSchool;
	
	private StudentAppData studentInfo;
	
	private String jmsMessage;
	
	private String kafkaMessage;
	

	public String getSchoolAppInfo() {
		return schoolAppInfo;
	}

	public void setSchoolAppInfo(String schoolAppInfo) {
		this.schoolAppInfo = schoolAppInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<School> getListSchool() {
		return listSchool;
	}

	public void setListSchool(List<School> listSchool) {
		this.listSchool = listSchool;
	}	

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public StudentAppData getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentAppData studentInfo) {
		this.studentInfo = studentInfo;
	}

	public String getJmsMessage() {
		return jmsMessage;
	}

	public void setJmsMessage(String jmsMessage) {
		this.jmsMessage = jmsMessage;
	}

	public String getKafkaMessage() {
		return kafkaMessage;
	}

	public void setKafkaMessage(String kafkaMessage) {
		this.kafkaMessage = kafkaMessage;
	}

	@Override
	public String toString() {
		return "SchoolAppData [schoolAppInfo=" + schoolAppInfo + ", message=" + message + ", schoolName=" + schoolName
				+ ", listSchool=" + listSchool + ", studentInfo=" + studentInfo + ", jmsMessage=" + jmsMessage
				+ ", kafkaMessage=" + kafkaMessage + "]";
	}

	
}