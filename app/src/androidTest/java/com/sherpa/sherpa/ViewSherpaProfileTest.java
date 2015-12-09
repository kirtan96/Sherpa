package com.sherpa.sherpa;

import android.test.ActivityInstrumentationTestCase2;

import com.sherpa.sherpa.Activites.ViewSherpaProfile;

/**
 * Created by Manas on 12/8/2015.
 */
public class ViewSherpaProfileTest extends ActivityInstrumentationTestCase2<ViewSherpaProfile>{

    public ViewSherpaProfileTest(){
        super(ViewSherpaProfile.class);
    }
    public void testUpdateRating(){
        ViewSherpaProfile vs = new ViewSherpaProfile();
        String name = "Rajesh";
        vs.updateRating(name);
        assertEquals(name, ViewSherpaProfile.testName);
    }
    public void testNavigateToRate(){
        ViewSherpaProfile vs = new ViewSherpaProfile();
        String name = "Rajesh";
        vs.navigateToRateHelper(name);
        assertEquals(name, ViewSherpaProfile.testName);
    }
}
