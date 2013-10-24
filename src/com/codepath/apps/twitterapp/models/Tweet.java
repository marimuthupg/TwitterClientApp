package com.codepath.apps.twitterapp.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.utils.JsonUtils;

public class Tweet implements Serializable {

	private User user;

	private long id;
	
	private String body;
	
	private String createdTime;
	
	public Tweet(JSONObject jsonObject) {
		id = JsonUtils.getLong(jsonObject, "id");
		body = JsonUtils.getString(jsonObject, "text");
		createdTime = JsonUtils.getString(jsonObject, "created_at");
	}

	public User getUser() {
		return user;
	}

	public String getBody() {
		return body;
	}

	public long getId() {
		return id;
	}

	public String getCreatedTime() {
		return createdTime;
	}
	
	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet(jsonObject);
		try {
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

		for (int i=0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}

}
