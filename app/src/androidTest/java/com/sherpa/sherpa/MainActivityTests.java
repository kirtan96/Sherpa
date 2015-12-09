package com.sherpa.sherpa;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import com.sherpa.sherpa.Activites.MainActivity;

/**
 * Created by yashp_000 on 12/8/2015.
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {
    private Button tg;
    private Button t;

    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testText()
    {
        MainActivity activity = getActivity();
        tg = (Button) activity.findViewById(R.id.tgButton);
        TouchUtils.clickView(this, tg);
        t = (Button) activity.findViewById(R.id.touristButton);
        TouchUtils.clickView(this, t);
    }
}

