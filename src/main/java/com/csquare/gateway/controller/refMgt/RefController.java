package com.csquare.gateway.controller.refMgt;

import java.util.List;

import javax.xml.bind.ValidationException;

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

public class RefController {

	//Ref_city_mgt_controller
	 	@RequestMapping(value = "/getAllRefCites/{offset}/{limit}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getAllRefCites(@PathVariable Integer offset,@PathVariable Integer limit) throws ValidationException {
		String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
		String allCities = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getAllRefCites/"+offset+"/"+limit, String.class);		
		return allCities;  	
	    }
	 
	 	@RequestMapping(value = "/addRefCity", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String addRefCity(@RequestBody String refcity) {
		 String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
		 String city = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "addRefCity",refcity.toString() , String.class);
	        return city;
	    }

	    @RequestMapping(value = "/updateRefCity", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String updateRefCity(@RequestBody String refcity) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
			 String city = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "updateRefCity",refcity.toString() , String.class);
		        return city;
	    }

	    @RequestMapping(value = "/deleteRefCity/{refcityId}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public void deleteRefCity(@PathVariable String refcityId) {
	    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "deleteRefCity/"+refcityId,"",String.class);
	    }

	    @RequestMapping(value = "/getRefCityById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getRefCityById(@PathVariable String id) throws ValidationException {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String cities = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getRefCityById/"+id, String.class);
			
			return cities;
	    }
	    
	    @RequestMapping(value = "/searchRefCity/{offset}/{limit}/{allMatch}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String searchRefCity(@RequestBody String criterias, @PathVariable Integer offset, @PathVariable Integer limit,
	        @PathVariable Boolean allMatch) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	    	
	    	String cities = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "searchRefCity/"+offset+"/"+limit+"/"+allMatch,criterias.toString(), String.class);
			
	        return cities;
	    }
	    
	    //End Ref_city_mgt Controller
	    
	    //Ref_Grade_mgt Controller
	    
	    @RequestMapping(value = "/addRefGrades", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String addRefGrades(@RequestBody String refGrade) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String grade = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "addRefGrades",refGrade.toString(), String.class);
			
	        return grade;
	    }

	    @RequestMapping(value = "/updateRefGrades", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String updateRefGrades(@RequestBody String refGrade) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String grade = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "updateRefGrades",refGrade.toString(), String.class);
			
	        return grade;
	    }
	    
	    @RequestMapping(value = "/deleteRefGrades/{refGradeId}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public void delteRefGrades(@PathVariable String refGradesId) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "deleteRefGrades/"+refGradesId,"", String.class);
			
	    }
	    
	    @RequestMapping(value = "/getRefGradesById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getRefGradesById(@PathVariable String id) {

	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String grade = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getRefGradesById/"+id, String.class);
	    	return grade;
	    }
	    
	    @RequestMapping(value = "/getAllRefGrades", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getAllRefGrades() {

	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String gradeList = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getAllRefGrades/", String.class);
	    	return gradeList;
	    }
	    
	    //End Ref_Grade_mgt Controller
	    
	    //Ref_Syllabus_mgt Controller
	    
	    @RequestMapping(value = "/addRefSyllabus", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String addRefSyllabus(@RequestBody String refSyllabus) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String s = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "addRefSyllabus", refSyllabus.toString(), String.class);
			
	        return s;
	    }

	    @RequestMapping(value = "/updateRefSyllabus", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String updateRefSyllabus(@RequestBody String refSyllabus) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String s = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "updateRefSyllabus", refSyllabus.toString(), String.class);
			
	        return s;
	    }
	    
	    @RequestMapping(value = "/deleteRefSyllabus/{refSyllabusId}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public void deleteRefSyllabus(@PathVariable String refSyllabusId) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "deleteRefSyllabus/"+refSyllabusId, "", String.class);
	    }
	    
	    @RequestMapping(value = "/getRefSyllabusById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getRefSyllabusById(@PathVariable String id) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String syllabus = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getRefSyllabusById/"+id, String.class);
	    	return syllabus;
	    }
	    
	    @RequestMapping(value = "/getAllRefSyllabus", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getAllRefSyllabus() {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String syllabusList = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getAllRefSyllabus/", String.class);
	    	return syllabusList;
	    }
	    
	    //End Ref_Syllabus_mgt Controller
	    
	    //Ref_Location_mgt Controller
	    
	    @RequestMapping(value = "/addRefLocation", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String addRefLocation(@RequestBody String refLocation) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String s = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "addRefLocation", refLocation.toString(), String.class);
			
	        return s;
	    }

	    @RequestMapping(value = "/updateRefLocation", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String updateRefLocation(@RequestBody String refLocation) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String s = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "updateRefLocation", refLocation.toString(), String.class);
			
	        return s;
	    }

	    @RequestMapping(value = "/deleteRefLocation/{refLocationId}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public void deleteRefLocation(@PathVariable String refLocationId) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "deleteRefLocation/"+refLocationId, "", String.class);
	    }

	    @RequestMapping(value = "/getRefLocationById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getRefLocationById(@PathVariable String id) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String location = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getRefLocationById/"+id, String.class);
	    	return location;
	    }

	    @RequestMapping(value = "/getAllRefLocations", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getAllRefLocations() {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String locationList = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getAllRefLocations/", String.class);
	    	return locationList;
	    }

	    @RequestMapping(value = "/searchLocationByCity/{city}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String searchLocationByCity(@PathVariable String city) {

	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String locationList = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "searchLocationByCity/"+city, String.class);
	    	return locationList;
	    }
	    
	    //End Ref_Location_mgt Controller
	    
	    //Ref_Subject_mgt Controller
	    
	    @RequestMapping(value = "/addRefSubject", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String addRefSubject(@RequestBody String refSubject) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String s = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "addRefSubject", refSubject.toString(), String.class);
			
	        return s;
	    }

	    @RequestMapping(value = "/updateRefSubject", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String updateRefSubject(@RequestBody String refSubject) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String s = RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "updateRefSubject", refSubject.toString(), String.class);
			
	        return s;
	    }
	    
	    @RequestMapping(value = "/deleteRefSubject/{refSubjectId}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public void deleteRefSubject(@PathVariable String refSubjectId) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	RestServiceClient.INSTANCE.postForObject(cs_ref_mgtURL + "deleteRefSubject/"+refSubjectId, "", String.class);
	    }
	    
	    @RequestMapping(value = "/getRefSubjectById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getRefSubjectById(@PathVariable String id) {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String subject = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getRefSubjectById/"+id, String.class);
	    	return subject;
	    }

	    @RequestMapping(value = "/getAllRefSubjects", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getAllRefSubjects() {
	    	String cs_ref_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_ref_mgt");
	    	String subjectList = RestServiceClient.INSTANCE.getForObject(cs_ref_mgtURL + "getAllRefSubjects", String.class);
	    	return subjectList;
	    }
	    
	    //End Ref_Location_mgt Controller
	    
}
