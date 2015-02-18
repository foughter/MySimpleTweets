package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeTweetActivity extends ActionBarActivity {

    private ImageView ivUserImage;
    private TextView tvUserScreenName;
    private User user;
    private TwitterClient client;
    private EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#4099FF"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        client = TwitterApplication.getRestClient();

        ivUserImage = (ImageView)findViewById(R.id.ivUserImage);
        tvUserScreenName = (TextView) findViewById(R.id.tvUserScreenName);
        etTweet =(EditText)findViewById(R.id.etTweet);

        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                if (user != null) {
                    Picasso.with(getApplicationContext()).load(user.getProfileImageUrl()).into(ivUserImage);
                    tvUserScreenName.setText(user.getScreenName());
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void compose(View view) {

        final String tweet = etTweet.getText().toString();

        if (tweet == null || tweet.length() == 0) {
            Toast.makeText(this, "empty tweet...", Toast.LENGTH_LONG).show();
            return;
        }

        client.composeTweet(tweet, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("DEBUG", "compose successful!");

                returnToTimeline(tweet);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("DEBUG", "compose failed!");
            }
        });
    }

    public void onCancel(View view) {
        this.finish();
    }

    private void returnToTimeline(String tweet) {
        Intent i = new Intent();

        i.putExtra("tweet", tweet);

        setResult(RESULT_OK, i);
        this.finish();
    }
}
