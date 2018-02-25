package com.csquare.gateway.controller.tutorMgt;

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

public class TutorContoller {

	 @RequestMapping(value = "/addTutor", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String addTutor(@RequestBody String tutor) {
	        /*
	         * if(tutor !=null
	         * && CSquareInputDataValidator.validatePhoneNumber(String.valueOf(tutor.getPhone()))
	         * && CSquareInputDataValidator.isValidEmailAddress(tutor.getEmail())){
	         * tutor = iTutorService.addTutor(tutor);
	         * 
	         * }
	         */
	        // MailMessage message = new MailMessage();
	        // message.setToAddress(tutor.getEmail());
	        // message.setSubject("Subject11111111");
	        // message.setBody("lead is created");
	        // RestServiceClient.INSTANCE.postForObject("http://localhost:8084/cs_communication_mgt/sendEmail", message, String.class);
	        //
		 String cs_tutor_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_tutor_mgt");
		 String t = RestServiceClient.INSTANCE.postForObject(cs_tutor_mgtURL + "addTutor",tutor.toString() , String.class);
	        return t;
	    }

	    @RequestMapping(value = "/updateTutor", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String updateTutor(@RequestBody String tutor) {

	        /*
	         * if(tutor !=null
	         * && CSquareInputDataValidator.validatePhoneNumber(String.valueOf(tutor.getPhone()))
	         * && CSquareInputDataValidator.isValidEmailAddress(tutor.getEmail())){
	         * tutor = iTutorService.updateTutor(tutor);
	         * 
	         * }
	         */
	    	String cs_tutor_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_tutor_mgt");
			 String t = RestServiceClient.INSTANCE.postForObject(cs_tutor_mgtURL + "updateTutor",tutor.toString() , String.class);
		        return t;
	    }

	    @RequestMapping(value = "/deleteTutor/{tutorId}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public void deleteTutor(@PathVariable String tutorId) {

	    	String cs_tutor_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_tutor_mgt");
	    	RestServiceClient.INSTANCE.postForObject(cs_tutor_mgtURL + "deleteTutor/"+tutorId,"",String.class);
	    }

	    @RequestMapping(value = "/getTutorById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getTutorById(@PathVariable String id) {
	    	String cs_tutor_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_tutor_mgt");
	    	String tutor = RestServiceClient.INSTANCE.getForObject(cs_tutor_mgtURL + "getTutorById/"+id, String.class);
	    	return tutor;
	    }

	    @RequestMapping(value = "/getAllTutors/{offset}/{limit}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getAllTutors(@PathVariable Integer offset, @PathVariable Integer limit) {
	    	String cs_tutor_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_tutor_mgt");
	    	String tutorList = RestServiceClient.INSTANCE.getForObject(cs_tutor_mgtURL + "getAllTutors/"+offset+"/"+limit, String.class);
	    	return tutorList;
	    }
	    
	    @RequestMapping(value = "/searchTutor/{offset}/{limit}/{allMatch}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String searchUser(@RequestBody String criterias, @PathVariable Integer offset, @PathVariable Integer limit,
	        @PathVariable Boolean allMatch) {
	    	String cs_tutor_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_tutor_mgt");
	    	String tutorList = RestServiceClient.INSTANCE.postForObject(cs_tutor_mgtURL + "searchTutor/"+offset+"/"+limit+"/"+allMatch, criterias.toString(), String.class);
	    	return tutorList;
	    }
	
}
