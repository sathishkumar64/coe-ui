package com.coeui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.coeui.model.CountryInfo;
import com.coeui.model.School;
import com.coeui.model.Student;
import com.coeui.security.TokenAuthentication;
import com.coeui.service.RestClientService;

@Controller
public class COEController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestClientService service;

	@RequestMapping(value = "/")
	public String notesList(Model model, @RequestHeader HttpHeaders headers, final Authentication authentication) {
		
		TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        if (tokenAuthentication == null) {
            return "redirect:/login";
        }	 
		
		//headers.setBearerAuth(accesstoken);
		List<CountryInfo> countryInfoList = service.getDeployedCountryList(headers);
		model.addAttribute("countryInfo", countryInfoList);
		return "index";
	}

	@GetMapping(path = "/coe/school", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAllSchool(Model model, @RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String accesstoken) {		
		headers.setBearerAuth(accesstoken);
		List<School> schoolList = service.findAllSchools(headers);
		model.addAttribute("schoolList", schoolList);
		return "get_all_school";
	}

	@GetMapping("/coe/school/create")
	public String showSignUpForm(School school) {
		return "create_school";
	}

	@PostMapping(path = "/createschool")
	public String saveSchool(Model model, School school, @RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String accesstoken) {
		String status = service.saveSchool(school, headers);
		headers.setBearerAuth(accesstoken);
		logger.info("Create school Status.................: {}", status);
		model.addAttribute("schoolList", service.findAllSchools(headers));
		return "get_all_school";
	}

	@GetMapping("/coe/school/getStudentsBySchool")
	public String showSearchForm(School school) {
		return "get_stu_school";
	}

	@PostMapping(path = "/getStudentsBySchool", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getStudentsBySchool(Model model, School school, @RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String  accesstoken) {
		headers.setBearerAuth(accesstoken);
		logger.info("Find school Status.................: {}", school.getSchoolname());
		model.addAttribute("studentInfo", service.getStudentsBySchool(school.getSchoolname(), headers));
		return "get_stu_school";
	}

	@GetMapping(path = "/api/v1/coe/school", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody School findSchoolById(@RequestParam(value = "schoolName") String schoolName) {
		return service.findSchoolByName(schoolName);
	}

	/** --------------------- Student Service------------------ */

	@GetMapping(path = "/coe/student", produces = MediaType.APPLICATION_JSON_VALUE)
	public String findAllStudent(Model model, @RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String accesstoken) {
		headers.setBearerAuth(accesstoken);
		List<Student> studentList = service.findAllStudent(headers);
		model.addAttribute("studentList", studentList);
		return "get_all_student";
	}

	@GetMapping("/coe/student/create")
	public String showSignUpStudentForm(Student student) {
		return "create_student";
	}

	@PostMapping(path = "/createstudent")
	public String saveStudent(Model model, Student student, @RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String accesstoken) {
		headers.setBearerAuth(accesstoken);
		service.saveStudent(student);
		List<Student> studentList = service.findAllStudent(headers);
		model.addAttribute("studentList", studentList);
		return "get_all_student";
	}

	@GetMapping(path = "/api/v1/coe/student", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getStudentsBySchoolName(@RequestParam(value = "schoolName") String schoolName) {		
		return service.getStudentsBySchoolName(schoolName);
	}

}
