package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by liyli on 2/4/15.
 */
public class Tweet {
    private String body;
    private User user;
    private long uid;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Tweet(User user, long uid, String body, String createdAt) {
        this.user = user;
        this.uid = uid;
        this.body = body;
        this.createdAt = createdAt;
    }

    public Tweet(){}

    // Deserialize the JSON
    // Tweet.fromJSON("{ ... }") => <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");

            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        for (int i = 0; i< jsonArray.length(); i++)
        {
            try {
                Tweet tweet = Tweet.fromJSON(jsonArray.getJSONObject(i));
                tweets.add(tweet);
            }catch (JSONException e) {
                continue;
            }
        }

        return tweets;
    }

}
