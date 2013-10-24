package com.codepath.apps.twitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		
	}
	
	private void cancelRequest(String msg) {
		Intent i = new Intent();
		i.putExtra("errorMessage", msg);
		setResult(RESULT_CANCELED, i);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

	
	public void onPostClick(View v) {
		EditText etTweet = (EditText) findViewById(R.id.etTweet);
		final String tweetText = etTweet.getText().toString().trim();
		if (tweetText.isEmpty()) {
			Toast.makeText(getApplicationContext(), "Empty tweet!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		TwitterClientApp.getRestClient().postTweet(tweetText, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonResponse) {
				Tweet tweet = new Tweet(jsonResponse);
				
				Intent i = new Intent();
				i.putExtra("composedTweet", tweet);
				setResult(RESULT_OK, i);
				finish();
			}
			
			@Override
			public void onFailure(Throwable t, JSONObject responseObject) {
				Log.e("ERROR", "Couldn't post tweet", t);
				cancelRequest("Could not post tweet. Please try again later.");
			}
			
		});
	}
}
