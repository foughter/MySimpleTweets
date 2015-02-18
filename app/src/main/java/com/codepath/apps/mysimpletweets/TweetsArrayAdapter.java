package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liyli on 2/4/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    final int SCREEN_NAME = 1;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets){
        super(context, 0, tweets);
    }

    //override and setup custom template


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get the tweet
        Tweet tweet = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // find the subview to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserView = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);

        // populate data into the subviews
        tvUserView.setText("@"+tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());

        String relativeTS = null;
        try {
            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").parse(tweet.getCreatedAt());

            relativeTS = timeHelper.getTimeAgo(date.getTime());
        }catch (ParseException e){
            relativeTS = "just now";
        }

        tvTimeStamp.setText(relativeTS);

        ivProfileImage.setImageResource(android.R.color.transparent); // clear out the old image for recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        // set tag and onClickListener so that clicking the profile image can go to the profile activity
        ivProfileImage.setTag(tweet.getUser().getScreenName());
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", (String)v.getTag());
                getContext().startActivity(i);
            }
        });

        // return the view
        return convertView;
    }
}
