package com.codepath.apps.mysimpletweets;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.mysimpletweets.com.codepath.apps.mysimpletweets.fragments.HomeTimelinePageFragment;
import com.codepath.apps.mysimpletweets.com.codepath.apps.mysimpletweets.fragments.MentionTimelinePageFragment;
import com.codepath.apps.mysimpletweets.com.codepath.apps.mysimpletweets.fragments.TimelinePageFragment;

/**
 * Created by liyli on 2/16/15.
 */
public class LaunchFragmentPagerAdapter extends FragmentPagerAdapter {

    final private String tabTitles[] = new String[] { "Home", "Mentions" };
    HomeTimelinePageFragment homeTimelinePageFragment;

    public LaunchFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        homeTimelinePageFragment = HomeTimelinePageFragment.newInstance();


    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return homeTimelinePageFragment;
        }
        if (position == 1)
            return MentionTimelinePageFragment.newInstance();

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}