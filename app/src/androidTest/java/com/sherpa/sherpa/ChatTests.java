package com.sherpa.sherpa;

import android.test.ActivityInstrumentationTestCase2;
import com.sherpa.sherpa.custom.CustomActivity;
import com.sherpa.sherpa.model.Conversation;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by yashp_000 on 12/8/2015.
 */
public class ChatTests extends ActivityInstrumentationTestCase2<Chat>
{
    private Chat myChat = new Chat();
    /**
     * Creates an {@link ActivityInstrumentationTestCase2}.
     */
    public ChatTests() {
        super(Chat.class);
    }

    @Test
    public void testActivityExists() {
        CustomActivity cm = getActivity();
        assertNotNull(cm);
    }
    //Tests
    public void testText() {
        ArrayList<Conversation> list = new Chat().getConvList();
        assertNotNull(list);
    }
}
