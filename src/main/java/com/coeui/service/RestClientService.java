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
import org.springframework.web.client.RestTemplate;

import com.coeui.model.CountryInfo;
import com.coeui.model.School;

@Service
public class RestClientService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${utility.api.url:http://localhost:8082}")
	private String remoteUtilityURL;	

	@Value("${school.api.url:http://schoolservice:9098/api/school}")
	private String remoteSchoolURL;

	@Value("${student.api.url:http://studentservice:8098/api/student}")
	private String remoteStudentURL;
	
	
	/** --------------------- Utility Service------------------ */
	
	
	
	public List<CountryInfo> getDeployedCountryList(HttpHeaders headers) {
		logger.info("Fetching Deployed Country List from Utility Service.......");		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<List<CountryInfo>> response= restTemplate.exchange(remoteUtilityURL + "/deployedCountryInfo", HttpMethod.GET, entity, new ParameterizedTypeReference<List<CountryInfo>>(){});
		List<CountryInfo> countryInfoList = response.getBody();		
		return countryInfoList;
	}
	
		
	
	/** --------------------- School Service------------------ */
	
	

	public List<School> findAllSchools(HttpHeaders headers) {
		logger.info("Fetching School List from School Service.......");
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<List<School>> response= restTemplate.exchange(remoteSchoolURL + "/all", HttpMethod.GET, entity, new ParameterizedTypeReference<List<School>>(){});
		List<School> schools = response.getBody();		
		return schools;
	}

	public School findSchoolByName(String schoolName) {
		logger.info("{} ?schoolname= {}", remoteSchoolURL, schoolName);
		return restTemplate.getForObject(remoteSchoolURL + "?schoolname=" + schoolName, School.class);
	}

	public String saveSchool(School school) {
		logger.info("Creating new school {}", school.getSchoolname());
		return restTemplate.postForObject(remoteSchoolURL + "/create", school, String.class);
	}

	public String getStudentsBySchool(String schoolName,HttpHeaders headers) {
		logger.info("Sending Header Info from COE Service::::::::: {}",headers);	
		logger.info("Getting Student List by School Name {}", schoolName);		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response= restTemplate.exchange(remoteSchoolURL + "/getStudentsBySchool/" + schoolName, HttpMethod.GET, entity, new ParameterizedTypeReference<String>(){});
		return response.getBody();
	}
	
	
	/** --------------------- Student Service------------------ */

	/*
	 * public List<Student> findAllStudent(HttpHeaders headers) {
	 * logger.info("Fetching Studnet List from Student Service .......");
	 * HttpEntity<?> entity = new HttpEntity<>(headers);
	 * ResponseEntity<List<Student>> response=
	 * restTemplate.exchange(remoteStudentURL + "/all", HttpMethod.GET, entity, new
	 * ParameterizedTypeReference<List<Student>>(){}); List<Student> students =
	 * response.getBody(); return students; }
	 * 
	 * public String getStudentsBySchoolName(String schoolName) {
	 * logger.info("{}/getStudentDetailsForSchool/{}", remoteStudentURL,
	 * schoolName); return restTemplate.getForObject(remoteStudentURL +
	 * "/getStudentDetailsForSchool/" + schoolName, String.class); }
	 * 
	 * public String saveStudent(Student student) {
	 * logger.info("Creating new student for this {}", student.getSchoolname());
	 * return restTemplate.postForObject(remoteStudentURL + "/createstudent",
	 * student, String.class); }
	 */

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}