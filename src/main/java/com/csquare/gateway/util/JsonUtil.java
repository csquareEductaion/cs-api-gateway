package com.csquare.gateway.util;

import org.json.JSONObject;

public class JsonUtil {
	
	public static String getString(JSONObject jsonObj,String key) {
		
		if(jsonObj.isNull(key)) {
			return null;
		}
		return jsonObj.getString(key);
	}
	
	public static int getInt(JSONObject jsonObj,String key) {
		
		if(jsonObj.isNull(key)) {
			return 0;
		}
		return jsonObj.getInt(key);
	}

	public static boolean getBoolean(JSONObject jsonObj,String key) {
		
		if(jsonObj.isNull(key)) {
			return false;
		}
		return jsonObj.getBoolean(key);
	}
	
	public static double getDouble(JSONObject jsonObj,String key) {
		
		if(jsonObj.isNull(key)) {
			return 0;
		}
		return jsonObj.getDouble(key);
	}
	
	public static long getLong(JSONObject jsonObj,String key) {
		
		if(jsonObj.isNull(key)) {
			return 0;
		}
		return jsonObj.getLong(key);
	}
}
