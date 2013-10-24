package com.codepath.apps.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	public static String getString(JSONObject jsonObject, String name) {
		if(jsonObject != null) {
			try {
				return jsonObject.getString(name);
			} catch (JSONException e) {
			}
		}
		return "";
	}
	
	public static long getLong(JSONObject jsonObject, String name) {
		if(jsonObject != null) {
			try {

				return jsonObject.getLong(name);

			} catch (JSONException e) {

			}
		}
		return 0;
	}

	public static int getInt(JSONObject jsonObject, String name) {

		if(jsonObject != null) {
			try {
				return jsonObject.getInt(name);
			} catch (JSONException e) {
			}
		}

		return 0;
	}

	public static double getDouble(JSONObject jsonObject, String name) {

		if(jsonObject != null) {
			try {
				return jsonObject.getDouble(name);
			} catch (JSONException e) {
			}
		}

		return 0;
	}

	public static boolean getBoolean(JSONObject jsonObject, String name) {
		if(jsonObject != null) {
			try {
				return jsonObject.getBoolean(name);
			} catch (JSONException e) {
			}
		}

		return false;
	}


}
