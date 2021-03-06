package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.sherpa.sherpa.HelperClasses.SherpaProfile;
import com.sherpa.sherpa.R;
import com.sherpa.sherpa.UserList;

/**
 * The Class MainActivity is the Activity class that holds the main screen and is activated
 * at the start of the app. It checks if the user is logged in and prompts them to become
 * a tourist or tour guide. It also allows the user to logout.
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    protected TextView welcome;
    protected Button tg;
    protected Button t;
    private static SherpaProfile currentUser;

    /**
     * It creates the content of the main activity and displays it to the current user.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        currentUser = new SherpaProfile(ParseUser.getCurrentUser());
        if(currentUser.isNull())
        {
            navigateToLogin();
        }
        else
        {
            currentUser.setChattingWith("");
            currentUser.setOnline(true);
            currentUser.setLoggedIn(true);
            currentUser.saveUser();
            welcome = (TextView) findViewById(R.id.welcome);

            tg = (Button) findViewById(R.id.tgButton);
            t = (Button) findViewById(R.id.touristButton);
            welcome.setText("Welcome " + currentUser.getFirstname() + " " +
                    currentUser.getLastname());
            tg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!currentUser.hasCreatedProfile()) {
                        navigateToEditProfile();
                    } else {
                        navigateToViewProfile(); //need to change this
                    }
                }
            });
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToSearch();
                }
            });
        }
    }

    /**
     * It takes the current user to the search activity class
     */
    private void navigateToSearch() {
        Intent intent = new Intent(MainActivity.this, SearchSherpa.class);
        startActivity(intent);
    }

    /**
     * It takes the current user to the activity where it displays the current user's tour
     * guide profile.
     */
    private void navigateToViewProfile() {
        Intent intent = new Intent(MainActivity.this, ViewProfile.class);
        startActivity(intent);
    }

    /**
     * It loads the menu on the main activity
     * @param menu
     * @return true if the action bar is present or false.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * When the current user comes back on this activity class, it loads and updates all the information of the
     * current user
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(!currentUser.isNull()) {
            currentUser.setOnline(true);
            currentUser.setChattingWith("");
            currentUser.setLoggedIn(true);
            currentUser.saveUser();
        }
    }

    /**
     * When the current user leaves this activity class, it updates the current user's profile
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(!currentUser.isNull()) {
            currentUser.setOnline(false);
            currentUser.saveUser();
        }
    }

    /**
     * It checks the item that the user selected and acts upon it
     * @param item - the item that the user selected from the menu
     * @return - true if the item selected matches the id or else it is false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            currentUser.setOnline(false);
            currentUser.setLoggedIn(false);
            currentUser.saveUser();
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.deleteInBackground();
            installation.saveInBackground();
            ParseUser.logOutInBackground();
            navigateToLogin();
        }
        else if(id == R.id.action_chat)
        {
            navigateToChat();
        }
        else
        {
            navigateToEditProfile();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * It takes the current user to the class that displays user list of chat
     */
    private void navigateToChat() {
        Intent intent = new Intent(MainActivity.this, UserList.class);
        startActivity(intent);
    }

    /**
     * It takes the current user to the activity where the user can edit their profile
     */
    private void navigateToEditProfile()
    {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    /**
     * It takes the current user to the login screen
     */
    private void navigateToLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}