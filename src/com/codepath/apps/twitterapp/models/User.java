package com.codepath.apps.twitterapp.models;

import java.io.Serializable;

import org.json.JSONObject;

import com.codepath.apps.utils.JsonUtils;

public class User implements Serializable {
	
	private String name;
	
	private String screenName;
	
	private long id;
	
	private int numTweets;
	
	private String profileImage;
	
	
	public User(JSONObject jsonObject) {
		name = JsonUtils.getString(jsonObject,"name");
		screenName =  JsonUtils.getString(jsonObject,"screen_name");
		profileImage = JsonUtils.getString(jsonObject,"profile_image_url");
		id = JsonUtils.getLong(jsonObject,"id");
		numTweets = JsonUtils.getInt(jsonObject,"statuses_count");
	}
	
    public String getName() {
    	return name;
    }

    public long getId() {
    	return id;
    }

    public String getScreenName() {
    	return screenName;
    }

    public String getProfileImageUrl() {
    	return profileImage;
    }

    public int getNumTweets() {
    	return numTweets;
    }

    public static User fromJson(JSONObject json) {
        return new User(json);
    }
   

}