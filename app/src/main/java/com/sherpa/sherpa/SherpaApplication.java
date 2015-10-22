package com.sherpa.sherpa;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Kirtan on 10/21/15.
 */
public class SherpaApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "TTMfZknhycL6KwMionAZYUTK2EkuYrrkVXW0q2NS", "HvqqLMGrE9XUpFPjdbAAVeqOzHYoSRPiBM7NQcTa");
    }
}
