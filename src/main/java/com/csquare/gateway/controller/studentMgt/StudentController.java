package com.csquare.gateway.controller.studentMgt;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csquare.framework.util.PropertyUtil;
import com.csquare.gateway.util.RestServiceClient;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

public class StudentController {
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST, headers = "Accept=application/json")
    public String addStudent(@RequestBody String student) {
		 String cs_student_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_student_mgt");
		 String s = RestServiceClient.INSTANCE.postForObject(cs_student_mgtURL + "addStudent",student.toString() , String.class);
	        return s;
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST, headers = "Accept=application/json")
    public String updateStudent(@RequestBody String student) {
    	 String cs_student_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_student_mgt");
		 String s = RestServiceClient.INSTANCE.postForObject(cs_student_mgtURL + "updateStudent",student.toString() , String.class);
	        return s;
    }
    
    @RequestMapping(value = "/deleteStudent/{studentId}", method = RequestMethod.POST, headers = "Accept=application/json")
    public void deleteStudent(@PathVariable String studentId) {
    	String cs_student_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_student_mgt");
    	RestServiceClient.INSTANCE.postForObject(cs_student_mgtURL + "deleteStudent/"+studentId,"",String.class);
    }
    
    @RequestMapping(value = "/getStudentById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getStudentById(@PathVariable String id) {
    	String cs_student_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_student_mgt");
    	String student = RestServiceClient.INSTANCE.getForObject(cs_student_mgtURL + "getStudentById/"+id, String.class);
    	return student;
    }

    @RequestMapping(value = "/getAllStudents/{offset}/{limit}", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getAllStudents(@PathVariable Integer offset, @PathVariable Integer limit) {
    	String cs_student_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_student_mgt");
    	String studentList = RestServiceClient.INSTANCE.getForObject(cs_student_mgtURL + "getAllStudents/"+offset+"/"+limit, String.class);
    	return studentList;
    }
    
    @RequestMapping(value = "/deleteStudentTutor/{studentId}", method = RequestMethod.POST, headers = "Accept=application/json")
    public void deleteStudentTutor(@PathVariable String studentId) {
    	String cs_student_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_student_mgt");
    	RestServiceClient.INSTANCE.postForObject(cs_student_mgtURL + "deleteStudentTutor/"+studentId,"",String.class);
    }

}
