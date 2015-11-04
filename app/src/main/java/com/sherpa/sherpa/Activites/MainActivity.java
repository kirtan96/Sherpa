package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;
import com.sherpa.sherpa.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    protected TextView welcome;
    protected Button tg;
    protected Button t;
    ParseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        currentUser = ParseUser.getCurrentUser();
        if(currentUser == null)
        {
            navigateToLogin();
        }
        else
        {

            currentUser.put("chattingWith", "");
            currentUser.put("online", true);
            currentUser.saveInBackground();
            welcome = (TextView) findViewById(R.id.welcome);
            tg = (Button) findViewById(R.id.tgButton);
            t = (Button) findViewById(R.id.touristButton);
            welcome.setText("Welcome " + currentUser.getString("firstname") + " " +
                    currentUser.getString("lastname"));
            tg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!currentUser.getBoolean("profile")) {
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

    private void navigateToSearch() {
        Intent intent = new Intent(MainActivity.this, SearchSherpa.class);
        startActivity(intent);
    }

    private void navigateToViewProfile() {
        Intent intent = new Intent(MainActivity.this, ViewProfile.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(currentUser != null) {
            currentUser.put("online", false);
            currentUser.saveInBackground();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            navigateToLogin();
        }
        else
        {
            navigateToEditProfile();
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToEditProfile()
    {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}