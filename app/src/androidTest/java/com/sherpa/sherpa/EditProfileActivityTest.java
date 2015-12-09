package com.sherpa.sherpa;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.RadioButton;
import com.sherpa.sherpa.Activites.EditProfileActivity;

import org.junit.Test;

/**
 * Created by yashp_000 on 12/8/2015.
 */
public class EditProfileActivityTest extends ActivityInstrumentationTestCase2<EditProfileActivity>
{
    private EditProfileActivity profileActivity;
    private RadioButton avyes;
    private RadioButton avno;
    /**
     * Creates an {@link ActivityInstrumentationTestCase2}.
     */
    public EditProfileActivityTest() {
        super(EditProfileActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        profileActivity = getActivity();
    }
    @Test
    public void testActivityExists() {
       assertNotNull(profileActivity);
    }
    @Test
    public void testForEditingAvailability()
    {
        avyes = (RadioButton) profileActivity.findViewById(R.id.avyes);
        avno = (RadioButton) profileActivity.findViewById(R.id.avno);

        profileActivity.setAv(false);
        assertEquals(false, avyes.isChecked());
        assertEquals(true, avno.isChecked());
    }
}
