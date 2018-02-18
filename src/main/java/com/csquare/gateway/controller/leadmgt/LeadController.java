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
import com.csquare.gateway.util.StringUtil;


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

        String cs_lead_mgtURL = PropertyUtil.INSTANCE.getProperty("cs_lead_mgt");
        String cs_user_mgtURL = PropertyUtil.INSTANCE.getProperty("cs_user_mgt");
        String cs_student_mgtURL = PropertyUtil.INSTANCE.getProperty("cs_student_mgt");
        String cs_tutor_mgtURL = PropertyUtil.INSTANCE.getProperty("cs_tutor_mgtURL");

        JSONObject jsonObj = new JSONObject(json);
        // String json = jsonObj.toString();
        String firstName = jsonObj.getString("firstName");
        String lastName = jsonObj.getString("lastName");
        String email = jsonObj.getString("email");
        String isStudent = jsonObj.getString("isstudent");
        // JSONArray leadGradeList = jsonObj.getJSONArray("leadGradeList");
        // String gradeId0 =
        // leadGradeList.getJSONObject(0).getString("gradeId");
        // String gradeId1 =
        // leadGradeList.getJSONObject(1).getString("gradeId");

        RestServiceClient.INSTANCE.postForObject(cs_lead_mgtURL + "addLead", json, String.class);

        JSONObject user = new JSONObject();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("email", email);
        RestServiceClient.INSTANCE.postForObject(cs_user_mgtURL + "addUser", user.toString(), String.class);

        boolean isStud = StringUtil.equalsIgnoreCase("true", isStudent);
        if (isStud) {
            JSONObject student = new JSONObject();
            student.put("firstName", firstName);
            student.put("lastName", lastName);
            student.put("email", email);
            RestServiceClient.INSTANCE.postForObject(cs_student_mgtURL + "addStudent", student, String.class);
        } else {
            JSONObject tutor = new JSONObject();
            tutor.put("firstName", firstName);
            tutor.put("lastName", lastName);
            tutor.put("email", email);
            RestServiceClient.INSTANCE.postForObject(cs_tutor_mgtURL + "addTutor", tutor, String.class);
        }

        return json;
    }

}
