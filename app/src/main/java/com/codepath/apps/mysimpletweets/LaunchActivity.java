package com.codepath.apps.mysimpletweets;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.com.codepath.apps.mysimpletweets.fragments.HomeTimelinePageFragment;
import com.codepath.apps.mysimpletweets.com.codepath.apps.mysimpletweets.fragments.TimelinePageFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;


public class LaunchActivity extends ActionBarActivity {
    final  int REQUEST_CODE_COMPOSE = 50;
    ViewPager viewPager;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new LaunchFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);


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

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);

        startActivity(i);
    }

    public void onCompose(MenuItem item) {
        Intent i = new Intent(this, ComposeTweetActivity.class);

        startActivityForResult(i, REQUEST_CODE_COMPOSE);
    }

    // This is a big hack!!!
    private String getFragmentTag()
    {
        return "android:switcher:" + viewPager.getId() + ":" + 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle the form data
        if (requestCode == REQUEST_CODE_COMPOSE) {
            if (resultCode == RESULT_OK) {

                HomeTimelinePageFragment fragment =  (HomeTimelinePageFragment)
                        getSupportFragmentManager().findFragmentByTag(getFragmentTag());

                String tweetBody = data.getStringExtra("tweet");
                fragment.onComposeTweet(tweetBody);


//                if (user != null) {
//                    // only insert the tweet in timeline if the user is fetched
//                    String tweetBody = data.getStringExtra("tweet");
//                    long uid = user.getUid();
//                    String createdAt = "now";
//
//                    Tweet tweet = new Tweet(user, uid, tweetBody, createdAt);
//                    // insert the tweet at the beginning of the stream
//                    aTweets.insert(tweet, 0);
//
//                }


            }
        }
    }
}
