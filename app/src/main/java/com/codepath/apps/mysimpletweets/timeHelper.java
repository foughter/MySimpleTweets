package com.codepath.apps.mysimpletweets;

/**
 * Created by liyli on 2/8/15.
 */
public class timeHelper {

    public static String getTimeAgo(long previousTimeInMilli) {

        long currentTime = System.currentTimeMillis();
        long diffInSec = (currentTime - previousTimeInMilli)/1000;

        if (diffInSec < 60)
            return "just now";

        if (diffInSec/60 < 2)
            return diffInSec/60 + " min ago";

        if (diffInSec/60 < 60)
            return diffInSec/60 + " mins ago";

        if (diffInSec/60/60 < 2)
            return diffInSec/60/60 + " hour ago";

        if (diffInSec/60/60 < 24)
            return diffInSec/60/60 + " hours ago";

        if (diffInSec/60/60/24 < 2)
            return diffInSec/60/60/24 + " day ago";

        if (diffInSec/60/60/24 < 7)
            return diffInSec/60/60/24 + " days ago";

        if (diffInSec/60/60/24/7 < 2)
            return diffInSec/60/60/24/7 + " week ago";

        if (diffInSec/60/60/24/7 <= 4)
            return diffInSec/60/60/24/7 + " weeks ago";

        if (diffInSec/60/60/24/30 < 2)
            return diffInSec/60/60/24/30 + " month ago";

        if (diffInSec/60/60/24/30 < 12)
            return diffInSec/60/60/24/30 + " months ago";

        return "over a year ago";
    }
}
