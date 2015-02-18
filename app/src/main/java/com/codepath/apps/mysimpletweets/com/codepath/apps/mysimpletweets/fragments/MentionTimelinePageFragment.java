package com.codepath.apps.mysimpletweets.com.codepath.apps.mysimpletweets.fragments;

import android.util.Log;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by liyli on 2/17/15.
 */
public class MentionTimelinePageFragment extends TimelinePageFragment {

    public static MentionTimelinePageFragment newInstance() {
        MentionTimelinePageFragment fragment = new MentionTimelinePageFragment();
        return fragment;
    }

    @Override
    protected void customLoadMoreDataFromApi(int offset){
        Log.i("INFO", "load more " + offset);

        if (tweets.size() == 0)
            return;

        Tweet lastTweet = tweets.get(tweets.size()-1);
        long max_id = lastTweet.getUid();

        client.getMentionTimeline(max_id, new JsonHttpResponseHandler() {
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

        client.getMentionTimeline(new JsonHttpResponseHandler() {
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
                Log.d("DEBUG", "mentions failure..." + errorResponse.toString());
            }
        });
    }

}
