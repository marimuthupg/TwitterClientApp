package com.codepath.apps.twitterapp;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterapp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}

		// Get the data item
		Tweet tweet = getItem(position); 

		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		TextView tvHome = (TextView) view.findViewById(R.id.tvBody);
		
		if(tweet != null && tweet.getUser() !=null) {
			if(tweet.getUser().getProfileImageUrl() != null) {
				ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
			}
			
			String formattedUserName = "<b>"+tweet.getUser().getName()+"</b>"+"<small><font color='#777777'> ( <i>" + tweet.getCreatedTime()+ "</i>)" +"</font></small>";
			tvName.setText(Html.fromHtml(formattedUserName));

		}
		
		tvHome.setText(Html.fromHtml(tweet.getBody()));

		return view; 
	}

}
