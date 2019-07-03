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
import com.coeui.security.TokenUtils;
import com.coeui.service.RestClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="COE-Istio Implementation", description="Operations pertaining to COE - School & Service")
@Controller
public class COEController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestClientService service;


	
	@RequestMapping(value="/home")
    public String notesList(Model model,@RequestHeader HttpHeaders headers, final Authentication authentication,@SessionAttribute("x-api-key") String  apikey) {			
		  TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
	        if (tokenAuthentication == null) {
	            return "redirect:/login";
	        }	        
	        headers.add("x-api-key", apikey);
		    String profileJson = TokenUtils.claimsAsJson(tokenAuthentication.getClaims());
	        model.addAttribute("profile", tokenAuthentication.getClaims());
	        model.addAttribute("profileJson", profileJson);
	        
	        
		List<CountryInfo> countryInfoList=service.getDeployedCountryList(headers);
        model.addAttribute("countryInfo", countryInfoList);        
        return "coe-ui";
    }
	
		
	
	@ApiOperation(value = "View a list of available school", response = Iterable.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping(path="/api/v1/coe/school/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public String  getAllSchool(Model model,@RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String accesstoken,@SessionAttribute("x-api-key") String  apikey) {		
		headers.setBearerAuth(accesstoken);
		headers.add("x-api-key", apikey);
		List<School> schoolList= service.findAllSchools(headers);		       
		model.addAttribute("schoolList", schoolList); 
		return "get_all_school";
	}
	
	
	
	  @GetMapping("/api/v1/coe/school")
	  public String showSignUpForm(School school) {
	        return "create_school";
	 }
	
	
	
	@ApiOperation(value = "Add a new School")
	@PostMapping(path="/createschool" )
	public String saveSchool (Model model, School school,@RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String  accesstoken,@SessionAttribute("x-api-key") String  apikey) {	
		headers.setBearerAuth(accesstoken);
		headers.add("x-api-key", apikey);
		String status= service.saveSchool(school,headers);	
		logger.info("Create school Status.................: {}",status);		
        model.addAttribute("schoolList", service.findAllSchools(headers));
        return "get_all_school";
	}
	
	
	 @GetMapping("/api/v1/coe/school/getStudentsBySchool")
	  public String showSearchForm(School school) {
	        return "get_stu_school";
	 }
	
	@ApiOperation(value = "Search a Student List with an School Name",response = String.class)
	@PostMapping(path="/getStudentsBySchool",produces = MediaType.APPLICATION_JSON_VALUE)
	public String getStudentsBySchool(Model model,School school,@RequestHeader HttpHeaders headers,@SessionAttribute("accesstoken") String  accesstoken,@SessionAttribute("x-api-key") String  apikey) {	
		headers.setBearerAuth(accesstoken);
		headers.add("x-api-key", apikey);
		logger.info("Find school Status.................: {}",school.getSchoolname());
		model.addAttribute("studentInfo", service.getStudentsBySchool(school.getSchoolname(),headers));
		return "get_stu_school";
	}
	
	
	
	@ApiOperation(value = "Search a school with an School Name",response = School.class)
	@GetMapping(path="/api/v1/coe/school",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody School findSchoolById(@RequestParam (value = "schoolName") String schoolName) {		
		return service.findSchoolByName(schoolName);
	}
	
	
	
	
	
		
	
	/** --------------------- Student Service------------------ */
	
	
	@ApiOperation(value = "View a list of available student", response = Iterable.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping(path="/api/v1/coe/student/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public String findAllStudent(Model model,@RequestHeader HttpHeaders headers) {
		List<Student> studentList=service.findAllStudent(headers);
		model.addAttribute("studentList", studentList); 
		return "get_all_student";
	}
	
	
	
	  @GetMapping("/api/v1/coe/student")
	 public String showSignUpStudentForm(Student student) {
	        return "create_student";
	 }
	
	
	@ApiOperation(value = "Add a new Student")
	@PostMapping(path="/createstudent" )
	public String saveStudent (Model model,Student student,@RequestHeader HttpHeaders headers) {		
	service.saveStudent(student);
	List<Student> studentList=service.findAllStudent(headers);
	model.addAttribute("studentList", studentList); 
		return "get_all_student";
	}
	
	
	@ApiOperation(value = "Search a Student List with an School Name",response = String.class)
	@GetMapping(path="/api/v1/coe/student",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getStudentsBySchoolName(@RequestParam (value = "schoolName") String schoolName) {		
		return service.getStudentsBySchoolName(schoolName);
	}
	 
}
