package com.csquare.gateway.controller.userMgt;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.csquare.framework.util.PropertyUtil;
import com.csquare.gateway.util.RestServiceClient;

public class AuthUser {

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public String login(@RequestBody String user) {
	
        
	 String cs_user_mgtURL = PropertyUtil.API_GATEWAY.getString("cs_user_mgt");
	 String u = RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "login",user.toString() , String.class);
        return u;
    }
}
