package com.codepath.apps.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends Activity {

	ArrayList<Tweet> tweets = new ArrayList<Tweet>();

	TweetsAdapter adapter = null;

	ListView lvTweets = null;
	
	User currentUser = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		lvTweets = (ListView) findViewById(R.id.tweetlistView);
		adapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(adapter);

		loadTweetsFromRemote(false);

		lvTweets.setOnScrollListener(new ContinousScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
				loadTweetsFromRemote(false);
			}
		});
	}

	public void onComposeAction(MenuItem mi) {
		
		Log.d("DEBUG", "On Compose action");
		// new intent to compose activity
		Intent i = new Intent(this, ComposeTweetActivity.class);
		startActivityForResult(i, 0);
	}
	
	public void onRefreshAction(MenuItem mi) {
		
		Log.d("DEBUG", "On refresh action");
		loadTweetsFromRemote(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}

	/**
	 * Result of compose
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == 0) {
    		if (data == null) {
    			return;
    		}
    		if (resultCode == RESULT_OK) {
    			Tweet tweet = (Tweet) data.getSerializableExtra("composedTweet");
    			tweets.add(0, tweet);
    			adapter.notifyDataSetChanged();
    		}
    		
    		if (resultCode == RESULT_CANCELED) {
    			Log.d("DEBUG", "Post cancelled");
    			String errorMessage = data.getStringExtra("errorMessage");
    			if (errorMessage != null) {
    				Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    			}
    		}
    	}
    }

	
	
	public void loadTweetsFromRemote(final boolean refresh) {
		String since_id = null;
		String max_id = null;

		if (this.currentUser == null) {
			TwitterClientApp.getRestClient().verifyCredentials(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject jsonCredentials) {
					currentUser = new User(jsonCredentials);
					setTitle("@"+currentUser.getScreenName());
				}
				
				@Override
				public void onFailure(Throwable t, JSONArray response) {
					Log.e("DEBUG", "Error getting current user data. Response="+response.toString(), t);
				}
			});
		}
		
		if(tweets != null && tweets.size()>0) {
			Tweet tweet = tweets.get(0);
			if(refresh && tweet != null) {
				since_id = Long.toString(tweet.getId());
			}

			tweet = tweets.get(tweets.size() - 1);

			if(tweet != null) {
				max_id = Long.toString(tweet.getId() - 1);
			}
		}
		
		
		
		TwitterClientApp.getRestClient().getTwitterHomeTimeLine("25", max_id, since_id, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				if(refresh) {
					tweets.addAll(0,Tweet.fromJson(jsonTweets));
				} else {
					tweets.addAll(Tweet.fromJson(jsonTweets));
				}
				adapter.notifyDataSetChanged();
			}

		});
		

	}
}
