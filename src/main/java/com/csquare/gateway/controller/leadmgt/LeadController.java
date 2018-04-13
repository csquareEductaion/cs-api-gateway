package com.csquare.gateway.controller.leadmgt;

import javax.xml.bind.ValidationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csquare.framework.message.MailMessage;
import com.csquare.framework.util.FileUtil;
import com.csquare.framework.util.PropertyUtil;
import com.csquare.framework.util.StringUtil;
import com.csquare.gateway.util.JsonUtil;
import com.csquare.gateway.util.RestServiceClient;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)


public class LeadController {
 

    @RequestMapping(value = "/addLead", method = RequestMethod.POST, headers = "Accept=application/json")
    public String addLead(@RequestBody String json) throws ValidationException {

        String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
        String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
        String cs_communication_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_communication_mgt");

        JSONObject jsonObj = new JSONObject(json);
        // String json = jsonObj.toString();
        String firstName = JsonUtil.getString(jsonObj, "firstName");
        String lastName = JsonUtil.getString(jsonObj,"lastName");
        String email = JsonUtil.getString(jsonObj,"email");
        String phone = JsonUtil.getString(jsonObj,"phone");
        String city = JsonUtil.getString(jsonObj,"city");
        Boolean isStudent = JsonUtil.getBoolean(jsonObj,"isstudent");
        Boolean isTutor = JsonUtil.getBoolean(jsonObj,"istutor");
        String gender = JsonUtil.getString(jsonObj,"gender");
        String location = JsonUtil.getString(jsonObj, "location");
        String alternate_phone = JsonUtil.getString(jsonObj,"alternatePhone");
        String address = JsonUtil.getString(jsonObj,"address");
        String user_role = null;
        if(isStudent.equals(true)) {
       	 	user_role =  "3";
        } 
	   	 if(isTutor.equals(true)) {
	   		 user_role =  "1";
	   	 } 
	   		String leadDb = RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "addLead", json, String.class);
	   		
	   		JSONObject leadFromDb = new JSONObject(leadDb);
	   		String status = JsonUtil.getString(leadFromDb, "firstName");
	   		
	   		String[] s = status.split("-");
	   		if(s[0].contentEquals("Present")) {
	   			return "Email already exists";
	   		} else {
	   			JSONObject user = new JSONObject();
	   	        user.put("firstName", firstName);
	   	        user.put("lastName", lastName);
	   	        user.put("email", email);
	   	        user.put("phone", phone);
	   	        user.put("city", city);
	   	        user.put("gender", gender);
	   	        user.put("location", location);
	   	        user.put("alternate_phone", alternate_phone);
	   	        user.put("address", address);
	   	        user.put("user_role", user_role);
	   	        user.put("user_status", "1");
	   	        String userUpdated = RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "addUser", user.toString(), String.class);
	   	        
	   	        JSONObject userFromDB = new JSONObject(userUpdated);
	   	        
	   	        String password = JsonUtil.getString(userFromDB, "password");
	   	        
	   	     MailMessage message = new MailMessage();
   	         message.setToAddress(email);
   	         message.setCcAddress("csquarebangalore@gmail.com");
	   	      message.setSubject("Enrollment Confirmation From CsquareEducation");
	          String body=FileUtil.LEAD_CREATE_MAIL_TEMPLATE.getFileAsString();
	          body = body.replace("{%Lead_Name%}",firstName + " " + lastName);
	          body = body.replace("{%UserName%}",email);
	          body = body.replace("{%Password%}", password);
	          if(isStudent.equals(true)) {
	        	  body = body.replace("{%leadType%}", "Student");
	          }
	          if(isTutor.equals(true)) {
	        	  body = body.replaceAll("{%leadType%}", "Tutor");
	          }
	          message.setBody(body);
   	         RestServiceClient.INSTANCE.postForObject(cs_communication_mgtURL+"sendEmail", message, String.class);
	   	        

	   	        return leadDb.toString();
	   		}

        
    }
    
    @RequestMapping(value = "/updateLead", method = RequestMethod.POST, headers = "Accept=application/json")
    public String updateLead(@RequestBody String json) throws ValidationException {
    	
    	 String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
         String cs_student_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_student_mgt");
         String cs_tutor_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_tutor_mgt");
         String cs_communication_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_communication_mgt");
         
         JSONObject jsonObj = new JSONObject(json);
         String firstName = JsonUtil.getString(jsonObj, "firstName");
         String lastName = JsonUtil.getString(jsonObj,"lastName");
         String email = JsonUtil.getString(jsonObj,"email");
         String phone = JsonUtil.getString(jsonObj,"phone");
         String city = JsonUtil.getString(jsonObj,"city");
         Boolean isStudent = JsonUtil.getBoolean(jsonObj,"isstudent");
         String gender = JsonUtil.getString(jsonObj,"gender");
         String alternate_phone = JsonUtil.getString(jsonObj,"alternatePhone");
         String address = JsonUtil.getString(jsonObj,"address");
         String location = JsonUtil.getString(jsonObj, "location");
         String leadStatus = JsonUtil.getString(jsonObj, "leadStatus");         
         JSONArray subjectArray = jsonObj.getJSONArray("leadSubjectList");	         
         JSONArray syllabusArray = jsonObj.getJSONArray("leadSyllabusList");
         String qualification = JsonUtil.getString(jsonObj, "qualification");
         
	          	int length = syllabusArray.length();
	          	JSONArray syllabusList = new JSONArray();
		         for(int i=0;i<length;i++) {
		        	 JSONObject syllabus=syllabusArray.getJSONObject(i);
		        	 JSONObject s = new JSONObject();
		        	 s.put("syllabusId", JsonUtil.getString(syllabus, "syllabusId"));
		        	 s.put("pk", JSONObject.NULL);	        	 	
		        	 s.put("tutor_id", JSONObject.NULL);
		        	 syllabusList.put(i, s); 	     	 	
		         }	  
	         
		         JSONArray gradeArray = jsonObj.getJSONArray( "leadGradeList");
         
		         length = gradeArray.length();
		         JSONArray gradeList = new JSONArray();
		         for(int i=0;i<length;i++) {
		        	 JSONObject grade = gradeArray.getJSONObject(i);
		        	 JSONObject gd = new JSONObject();
		        	 gd.put("gradeId", JsonUtil.getString(grade, "gradeId"));
		        	 gd.put("pk", JSONObject.NULL);	        	 	
		        	 gd.put("tutor_Id", JSONObject.NULL);
		        	 gradeList.put(i, gd);
		         }
	        
         RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "updateLead", json, String.class);
        
	         if(leadStatus.equals("4")) {
	        	 	          	
	        	 if (isStudent.equals(true)) {
	        		 int len=subjectArray.length();
	          	 	 JSONArray subjectList = new JSONArray();
	         	         for(int i=0;i<len;i++) {
	         	        	 JSONObject subject = subjectArray.getJSONObject(i);
	         	        	 JSONObject sub = new JSONObject();
	         	        	 sub.put("subjectId", JsonUtil.getString(subject, "subjectId"));
	         	        	 sub.put("pk", JSONObject.NULL);	        	 	
	         	        	 sub.put("studentId", JSONObject.NULL);
	         	        	 	 subjectList.put(i, sub);        	
	         	         }
	         	         
	         	        String syllabus = new String();
	    	          	if(length == 1) {
	    	          		JSONObject sy = syllabusArray.getJSONObject(0);
	    	          		syllabus = JsonUtil.getString(sy, "syllabusId");
	    	          	}
	    	          	
	    	          	 String grade = new String();	         
	    		          	if(length == 1) {
	    		          		JSONObject gd = gradeArray.getJSONObject(0);
	    		          		grade = JsonUtil.getString(gd, "gradeId");
	    		          	}
	    		          	
	                 JSONObject student = new JSONObject();
	                 student.put("firstName", firstName);
	                 student.put("lastName", lastName);
	                 student.put("email", email);
	                 student.put("phone", phone);
	                 student.put("city", city);
	                 student.put("gender", gender);
	                 student.put("alternatePhone", alternate_phone);
	                 student.put("address", address);
	                 student.put("studentSubjectList", subjectList);
	                 student.put("grade", grade);
	                 student.put("location", location);
	                 student.put("syllabus", syllabus);
	                 student.put("interested", true);
	                 RestServiceClient.INSTANCE.postForObject(cs_student_mgtURL + "addStudent", student.toString(), String.class);
	                 
	                 MailMessage message = new MailMessage();
		   	         message.setToAddress(email);
		   	         message.setCcAddress("csquarebangalore@gmail.com");
			   	      message.setSubject("Student Enrollment Confirmation From CsquareEducation");
			          String body=FileUtil.STUDENT_ENROLL_MAIL_TEMPLATE.getFileAsString();
			          body = body.replace("{%Student_Name%}",firstName + " " + lastName);
			          message.setBody(body);
		   	         RestServiceClient.INSTANCE.postForObject(cs_communication_mgtURL+"sendEmail", message, String.class);
	                 
	                 return student.toString();
	             } else {
	            	  int len=subjectArray.length();
	          	 	  JSONArray subjectList = new JSONArray();
	         	         for(int i=0;i<len;i++) {
	         	        	 JSONObject subject = subjectArray.getJSONObject(i);
	         	        	 JSONObject sub = new JSONObject();
	         	        	 sub.put("subjectId", JsonUtil.getString(subject, "subjectId"));
	         	        	 sub.put("pk", JSONObject.NULL);	        	 	
	         	        	 sub.put("tutor_id", JSONObject.NULL);
	         	        	 	 subjectList.put(i, sub);        	
	         	         }
	                 JSONObject tutor = new JSONObject();
	                 tutor.put("firstName", firstName);
	                 tutor.put("lastName", lastName);
	                 tutor.put("email", email);
	                 tutor.put("phone", phone);
	                 tutor.put("city", city);
	                 tutor.put("gender", gender);
	                 tutor.put("alternate_phone", alternate_phone);
	                 tutor.put("address", address);
	                 tutor.put("location", location);
	                 tutor.put("tutorSubjectList", subjectList);
	                 tutor.put("tutorGradeList", gradeList);
	                 tutor.put("tutorSyllabusList", syllabusList);
	                 tutor.put("qualification", qualification);
	                 tutor.put("is_interested_for_home_tuition", true);
	                 String t = RestServiceClient.INSTANCE.postForObject(cs_tutor_mgtURL + "addTutor", tutor.toString(), String.class);
	                 
	                 MailMessage message = new MailMessage();
		   	         message.setToAddress(email);
		   	         message.setCcAddress("csquarebangalore@gmail.com");
			   	      message.setSubject("Tutor Enrollment Confirmation From CsquareEducation");
			          String body=FileUtil.TUTOR_ENROLL_MAIL_TEMPLATE.getFileAsString();
			          body = body.replace("{%Tutor_Name%}",firstName + " " + lastName);
			          message.setBody(body);
		   	         RestServiceClient.INSTANCE.postForObject(cs_communication_mgtURL+"sendEmail", message, String.class);
	                 return tutor.toString();
	             }
	         } else {
	        	 return json;
	         }
    }
    
    @RequestMapping(value = "/deleteLead/{leadId}", method = RequestMethod.POST, headers = "Accept=application/json")
    public void deleteLead(@PathVariable String leadId) {

    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "deleteLead/"+leadId,"",String.class);
    }

    @RequestMapping(value = "/getLeadById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getLeadById(@PathVariable String id) {
      
        String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	String lead = RestServiceClient.INSTANCE.getForObject(cs_lead_mgtURL + "getLeadById/"+id, String.class);
		
		return lead;
    }

    @RequestMapping(value = "/getAllLeads/{offset}/{limit}", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getAllLeads(@PathVariable Integer offset, @PathVariable Integer limit) {

    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	String allLeads = RestServiceClient.INSTANCE.getForObject(cs_lead_mgtURL + "getAllLeads/"+offset+"/"+limit, String.class);
		
		return allLeads;
    }

    @RequestMapping(value = "/searchLead/{offset}/{limit}/{allMatch}", method = RequestMethod.POST, headers = "Accept=application/json")
    public String searchLead(@RequestBody String criterias, @PathVariable Integer offset, @PathVariable Integer limit,
        @PathVariable Boolean allMatch) {
    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	
    	String leadList = RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "searchLead/"+offset+"/"+limit+"/"+allMatch,criterias.toString(), String.class);
		
        return leadList;
    }
    
    @RequestMapping(value = "/getAllLeadStatus", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getAllLeadStatus() {

    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	String allLeadStatus = RestServiceClient.INSTANCE.getForObject(cs_lead_mgtURL + "getAllLeadStatus", String.class);
		
		return allLeadStatus;
    }
   
    @RequestMapping(value = "/getAllContact", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getAllContact() {

    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	String allContact = RestServiceClient.INSTANCE.getForObject(cs_lead_mgtURL + "getAllContact", String.class);
		
		return allContact;
    }
    
    @RequestMapping(value = "/deleteContact/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
    public void deleteContact(@PathVariable String id) {

    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "deleteContact/"+id,"",String.class);
    }
    
    @RequestMapping(value = "/contactUs", method = RequestMethod.POST, headers = "Accept=application/json")
    public String contactUs(@RequestBody String contact) {

    	String cs_lead_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_lead_mgt");
    	 contact = RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "contactUs",contact.toString(),String.class);
    	 
    	 return contact;
    }
    
}
