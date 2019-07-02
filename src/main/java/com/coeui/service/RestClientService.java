package com.coeui.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.coeui.model.CountryInfo;
import com.coeui.model.School;
import com.coeui.model.SchoolAppData;
import com.coeui.model.Student;

@Service
public class RestClientService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${utility.api.url:http://localhost:8082}")
	private String remoteUtilityURL;	

	@Value("${school.api.url}")
	private String remoteSchoolURL;

	@Value("${student.api.url}")
	private String remoteStudentURL;
	
	
	/** --------------------- Utility Service------------------ */

	
	public List<CountryInfo> getDeployedCountryList(HttpHeaders headers) {
		logger.info("Fetching Deployed Country List from Utility Service.......");		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<List<CountryInfo>> response=null;
		 try {
		      response= restTemplate.exchange(remoteUtilityURL + "/deployedCountryInfo", HttpMethod.GET, entity, new ParameterizedTypeReference<List<CountryInfo>>(){});
	
		 } catch (ResourceAccessException ex) {
		        throw new ResourceAccessException (ex.getLocalizedMessage());		       
		 }		
		List<CountryInfo> countryInfoList = response.getBody();		
		return countryInfoList;
	}
	
		
	
	/** --------------------- School Service------------------ */
	
	

	public List<School> findAllSchools(HttpHeaders headers) {
		logger.info("Fetching School List from School Service.......");	
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<List<School>> response=null;
		try {
		  response= restTemplate.exchange(remoteSchoolURL + "/all", HttpMethod.GET, entity, new ParameterizedTypeReference<List<School>>(){});
		 } catch (ResourceAccessException ex) {
		        throw new ResourceAccessException (ex.getLocalizedMessage());		       
		 }catch (HttpClientErrorException ex) {			
		     throw new IllegalArgumentException (ex.getResponseBodyAsString());		       
		 } 	
		List<School> schools = response.getBody();		
		return schools;
	}

	public School findSchoolByName(String schoolName) {
		logger.info("{} ?schoolname= {}", remoteSchoolURL, schoolName);
		try {		
		   return restTemplate.getForObject(remoteSchoolURL + "?schoolname=" + schoolName, School.class);
		} catch (ResourceAccessException ex) {
	        throw new ResourceAccessException (ex.getLocalizedMessage());		       
	     }catch (HttpClientErrorException ex) {			
		     throw new IllegalArgumentException (ex.getResponseBodyAsString());		       
		 } 	
	}

	public String saveSchool(School school,HttpHeaders headers) {
		logger.info("Creating new school {}", school.getSchoolname());
		try {	
			headers.set("Content-Type", "application/json");
			HttpEntity<?> request = new HttpEntity<>(school,headers);
			ResponseEntity<String> model = restTemplate.exchange(remoteSchoolURL + "/create", HttpMethod.POST, request, String.class);
			return model.getStatusCode().toString();
		} catch (ResourceAccessException ex) {
	        throw new ResourceAccessException (ex.getLocalizedMessage());		       
	     }
	}

	public SchoolAppData getStudentsBySchool(String schoolName,HttpHeaders headers) {
		logger.info("Sending Header Info from COE Service::::::::: {}",headers);	
		logger.info("Getting Student List by School Name {}", schoolName);		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<SchoolAppData> response= null;
		try {
		 response= restTemplate.exchange(remoteSchoolURL + "/getStudentsBySchool/" + schoolName, HttpMethod.GET, entity, new ParameterizedTypeReference<SchoolAppData>(){});
		 
		 logger.info("Getting Student List by School Name response ..........{}", response);		
		} catch (ResourceAccessException ex) {			
			logger.error("ResourceAccessException.................: {}",ex.getLocalizedMessage());			
	        throw new ResourceAccessException (ex.getLocalizedMessage());		       
	     }catch (HttpClientErrorException ex) {			
		     throw new IllegalArgumentException (ex.getResponseBodyAsString());		       
		 } 
		return response.getBody();
	}
	
	
	/** --------------------- Student Service------------------ */

	public List<Student> findAllStudent(HttpHeaders headers) {
		logger.info("Fetching Student List from Student Service .......");		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<List<Student>> response=null;
		try{
			 response= restTemplate.exchange(remoteStudentURL + "/all", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Student>>(){});
		} catch (ResourceAccessException ex) {
	        throw new ResourceAccessException (ex.getLocalizedMessage());		       
	     }catch (HttpClientErrorException ex) {			
		     throw new IllegalArgumentException (ex.getResponseBodyAsString());		       
		 } 		
		List<Student> students = response.getBody();		
		return students;
	}

	public String getStudentsBySchoolName(String schoolName) {
		logger.info("{}/getStudentDetailsForSchool/{}", remoteStudentURL, schoolName);
		try{
			return restTemplate.getForObject(remoteStudentURL + "/getStudentDetailsForSchool/" + schoolName, String.class);
		 } catch (ResourceAccessException ex) {
	        throw new ResourceAccessException (ex.getLocalizedMessage());		       
	     }catch (HttpClientErrorException ex) {			
		     throw new IllegalArgumentException (ex.getResponseBodyAsString());		       
		 } 	
	}

	public String saveStudent(Student student) {
		logger.info("Creating new student for this {}", student.getSchoolname());
		try{
			return restTemplate.postForObject(remoteStudentURL + "/createstudent", student, String.class);
		} catch (ResourceAccessException ex) {
	        throw new ResourceAccessException (ex.getLocalizedMessage());		       
	    }catch (HttpClientErrorException ex) {			
		     throw new IllegalArgumentException (ex.getResponseBodyAsString());		       
		 } 	
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}