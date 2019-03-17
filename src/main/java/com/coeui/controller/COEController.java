package com.coeui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coeui.model.School;
import com.coeui.model.Student;
import com.coeui.service.CountryService;
import com.coeui.service.RestClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="COE-Istio Implementation", description="Operations pertaining to COE - School & Service")
@Controller
public class COEController {

	@Autowired
	private RestClientService service;

	@Autowired
	private CountryService countryService;
	
	@RequestMapping(value="/")
    public String notesList(Model model) {
        model.addAttribute("countryInfo", countryService.getCountryInfo());
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
	public @ResponseBody Iterable<School> getAllSchool(@RequestHeader HttpHeaders headers) {		
		return service.findAllSchools(headers);
	}
	
	
	@ApiOperation(value = "Search a school with an School Name",response = School.class)
	@GetMapping(path="/api/v1/coe/school",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody School findSchoolById(@RequestParam (value = "schoolName") String schoolName) {		
		return service.findSchoolByName(schoolName);
	}
	
	@ApiOperation(value = "Add a new School")
	@PostMapping(path="/api/v1/coe/school" )
	public @ResponseBody String saveSchool (@RequestBody School school) {		
		return service.saveSchool(school);
	}
	
	@ApiOperation(value = "Search a Student List with an School Name",response = String.class)
	@GetMapping(path="/api/v1/coe/school/getStudentsBySchool",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getStudentsBySchool(@RequestParam (value = "schoolName") String schoolName,@RequestHeader HttpHeaders headers) {		
		return service.getStudentsBySchool(schoolName,headers);
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
	public @ResponseBody Iterable<Student> findAllStudent(@RequestHeader HttpHeaders headers) {
		return service.findAllStudent(headers);
	}
	
	@ApiOperation(value = "Search a Student List with an School Name",response = String.class)
	@GetMapping(path="/api/v1/coe/student",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getStudentsBySchoolName(@RequestParam (value = "schoolName") String schoolName) {		
		return service.getStudentsBySchoolName(schoolName);
	}
	
	@ApiOperation(value = "Add a new Student")
	@PostMapping(path="/api/v1/coe/student" )
	public @ResponseBody String saveStudent (@RequestBody Student student) {		
		return service.saveStudent(student);
	}
	
}
