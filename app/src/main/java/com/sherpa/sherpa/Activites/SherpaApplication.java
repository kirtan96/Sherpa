package com.sherpa.sherpa.Activites;

/**
 * Created by Kirtan on 10/21/15.
 */

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


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