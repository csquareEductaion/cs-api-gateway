package com.csquare.gateway.controller.leadmgt;

import javax.xml.bind.ValidationException;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csquare.gateway.util.PropertyUtil;
import com.csquare.gateway.util.RestServiceClient;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LeadController {

    @RequestMapping(value = "/getLead/{leadId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Object getLead(@PathVariable String leadId) throws ValidationException {

        // RestServiceClient.INSTANCE.postForObject(
        // "http://localhost:8001/cs_lead_mgt/addLead", lead,
        // String.class);
        // RestServiceClient.INSTANCE.postForObject(
        // "http://localhost:8001/cs_user_mgt/addUsert", lead,
        // String.class);
        //
        // RestServiceClient.INSTANCE.postForObject(
        // "http://localhost:8001/cs_user_mgt/addUsert", lead,
        // String.class);

        return leadId;
    }

    @RequestMapping(value = "/addLead", method = RequestMethod.POST, headers = "Accept=application/json")
    public String addLead(@RequestBody String json) throws ValidationException {

        JSONObject jsonObj = new JSONObject(json);
        // String json = jsonObj.toString();
        String firstName = jsonObj.getString("firstName");
        String lastName = jsonObj.getString("lastName");
        String email = jsonObj.getString("email");
        // JSONArray leadGradeList = jsonObj.getJSONArray("leadGradeList");
        // String gradeId0 =
        // leadGradeList.getJSONObject(0).getString("gradeId");
        // String gradeId1 =
        // leadGradeList.getJSONObject(1).getString("gradeId");
        String cs_lead_mgtURL = PropertyUtil.INSTANCE.getProperty("cs_lead_mgt");
        String cs_user_mgtURL = PropertyUtil.INSTANCE.getProperty("cs_user_mgt");

        RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "addLead", json, String.class);

        JSONObject user = new JSONObject();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("email", email);
        RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "addUser", user.toString(), String.class);

        // JSONObject student = new JSONObject();
        // user.put("firstName", firstName);
        // user.put("lastName", lastName);
        // user.put("email", email);
        // RestServiceClient.INSTANCE.postForObject(
        // "http://localhost:8001/cs_student_mgt/addStudent", student,
        // String.class);

        return json;
    }

}
