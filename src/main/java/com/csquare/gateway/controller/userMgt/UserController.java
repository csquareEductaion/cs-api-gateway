package com.csquare.gateway.controller.userMgt;

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

public class UserController {

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, headers = "Accept=application/json")
    public String addUser(@RequestBody String user) {
		// MailMessage message = new MailMessage();
        // message.setToAddress(user.getEmail());
        // message.setSubject("Subject11111111");
        // message.setBody("lead is created");
        // RestServiceClient.INSTANCE.postForObject("http://localhost:8084/cs_communication_mgt/sendEmail", message, String.class);
        
	 String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
	 String u = RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "addUser",user , String.class);
        return u;
    }

	   @RequestMapping(value = "/updateUser", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String updateUser(@RequestBody String user) {

    	String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
		 String u = RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "updateUser",user , String.class);
	        return u;
    }

	   @RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public void deleteUser(@PathVariable String userId) {

    	String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
    	RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "deleteUser/"+userId,"",String.class);
    }

	   @RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getUserById(@PathVariable String id) {
    	String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
    	String user = RestServiceClient.INSTANCE.getForObject(cs_user_mgtURL + "getUserById/"+id, String.class);
    	return user;
    }

	   @RequestMapping(value = "/getAllUsers/{offset}/{limit}", method = RequestMethod.GET, headers = "Accept=application/json")
	    public String getAllUsers(@PathVariable Integer offset, @PathVariable Integer limit) {
    	String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
    	String userList = RestServiceClient.INSTANCE.getForObject(cs_user_mgtURL + "getAllUsers/"+offset+"/"+limit, String.class);
    	return userList;
    }
    
	   @RequestMapping(value = "/searchUser/{offset}/{limit}/{allMatch}", method = RequestMethod.POST, headers = "Accept=application/json")
	    public String searchUser(@RequestBody String criterias, @PathVariable Integer offset, @PathVariable Integer limit,
	        @PathVariable Boolean allMatch) {
    	String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
    	String userList = RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "searchUser/"+offset+"/"+limit+"/"+allMatch, criterias.toString(), String.class);
    	return userList;
    }
	
}
