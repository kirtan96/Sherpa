package com.sherpa.sherpa.Activites;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

/**
 * Created by Kirtan on 10/21/15.
 */
public class SherpaApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "TTMfZknhycL6KwMionAZYUTK2EkuYrrkVXW0q2NS", "HvqqLMGrE9XUpFPjdbAAVeqOzHYoSRPiBM7NQcTa");

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
    public static void updateParseInstallation(ParseUser user)
    {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("userId", user.getObjectId());
        installation.saveInBackground();
    }
}
