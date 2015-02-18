package com.codepath.apps.mysimpletweets.com.codepath.apps.mysimpletweets.fragments;

import android.util.Log;
import android.view.View;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by liyli on 2/17/15.
 */
public class HomeTimelinePageFragment extends TimelinePageFragment {

    public static HomeTimelinePageFragment newInstance() {
        HomeTimelinePageFragment fragment = new HomeTimelinePageFragment();
        return fragment;
    }

    @Override
    protected void customLoadMoreDataFromApi(int offset){
        Log.i("INFO", "load more " + offset);

        if (tweets.size() == 0)
            return;

        Tweet lastTweet = tweets.get(tweets.size()-1);
        long max_id = lastTweet.getUid();

        //client.getMentionTimeline(max_id, new JsonHttpResponseHandler() {
        client.getHomeTimeline(max_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                Log.d("DEBUG", response.toString());
                // deserialize json
                // create model
                // load the model data in the listView
                aTweets.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    @Override
    protected void populateTimelineFirstTime(){
        //client.getMentionTimeline(new JsonHttpResponseHandler() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                Log.d("DEBUG", response.toString());
                // deserialize json
                // create model
                // load the model data in the listView
                tweets.clear();
                aTweets.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                //Log.d("DEBUG", errorResponse.toString());
                Log.d("DEBUG", "home timeline failure... " + errorResponse.toString());
            }
        });
    }

    public void onComposeTweet(String tweetBody) {

        long uid = user.getUid();
        String createdAt = "now";

        Tweet tweet = new Tweet(user, uid, tweetBody, createdAt);
        // insert the tweet at the beginning of the stream
        aTweets.insert(tweet, 0);

    }

}
