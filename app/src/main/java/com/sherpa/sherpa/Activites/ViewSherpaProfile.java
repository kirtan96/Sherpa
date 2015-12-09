package com.sherpa.sherpa.Activites;

/**
 * Created by Kirtan on 10/21/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.Chat;
import com.sherpa.sherpa.HelperClasses.SherpaProfile;
import com.sherpa.sherpa.R;

import java.util.List;


/**
 *
 */
public class ViewSherpaProfile extends AppCompatActivity {

    SherpaProfile user;
    String sherpaName;
    float rating;
    int rater;
    RatingBar ratingBar;
    public static String testName;

    /**
     * It creates the content of the view_sherpa_profile activity and displays it to the current user
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sherpa_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        sherpaName = username;

        ParseUser.getCurrentUser().put("online", true);
        ParseUser.getCurrentUser().saveInBackground();


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                for (final ParseUser u : list) {
                    if (u.getUsername().equals(username)) {
                        user = new SherpaProfile(u);

                        final ImageView profilePic = (ImageView) findViewById(R.id.profilePicture);
                        TextView name = (TextView) findViewById(R.id.name);
                        TextView city = (TextView) findViewById(R.id.city);
                        TextView places = (TextView) findViewById(R.id.listOfPlaces);
                        TextView cost = (TextView) findViewById(R.id.cost);
                        TextView email = (TextView) findViewById(R.id.email);
                        TextView transportation = (TextView) findViewById(R.id.transportation);
                        email.setText(user.getEmail());
                        TextView phone = (TextView) findViewById(R.id.phone);
                        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                        phone.setText(user.getPhone());

                        user.setProfilePic(profilePic);
                        transportation.setText(user.getTransportationString());
                        name.setText(user.getFirstname() + " " + user.getLastname());
                        city.setText(city.getText() + user.getCity());
                        places.setText(user.getPlaces());
                        String hour;
                        if (user.getCostType().equals("H")) {
                            hour = "hour";
                        } else {
                            hour = "day";
                        }
                        cost.setText(cost.getText() + "$" + user.getCost() + "/" + hour);
                        Button chatButton = (Button) findViewById(R.id.edit);
                        chatButton.setText("Chat with " + user.getFirstname() + " " + user.getLastname());
                        chatButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navigateToChat();
                            }
                        });

                        Button rateSherpa = (Button) findViewById(R.id.rateSherpa);
                        rateSherpa.setText("Rate " + user.getFirstname() + " " + user.getLastname());
                        rateSherpa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navigateToRate(u.getUsername());
                            }
                        });

                        updateRating(u.getUsername());
                    }
                }
            }
        });


    }

    /**
     * It takes the user to the class that displays a screen where the current user can rate the
     * tour guide
     * @param name - the name of the tour guide being rated
     */
    private void navigateToRate(String name) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Rating");
        query.whereEqualTo("RateTo", name);
        query.whereEqualTo("RateFrom", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                //testName = e.toString();
                if (list.isEmpty()) {
                    Intent intent = new Intent(ViewSherpaProfile.this, RatingSherpa.class);
                    intent.putExtra("username", sherpaName);
                    startActivity(intent);
                } else {
                    Toast.makeText(ViewSherpaProfile.this, "You have already rated this Sherpa!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void navigateToRateHelper(String n){
        navigateToRate(n);
    }
    /**
     * It takes the current user to the chat screen where the user can chat with the tour guide
     */
    private void navigateToChat()
    {
        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("username", sherpaName);
        startActivity(intent);
    }

    /**
     * When the current user leaves this activity class, it updates the current user's profile
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().put("online", false);
            ParseUser.getCurrentUser().saveInBackground();
        }
    }

    /**
     * When the current user comes back on this activity class, it loads and updates all the
     * information of the current user
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().put("online", true);
            ParseUser.getCurrentUser().saveInBackground();
        }
        updateRating(getIntent().getStringExtra("username"));
    }

    /**
     * It updates the current rating for the tour guide being rated
     * @param name - the name of the tour guide being rated
     */
    public void updateRating(String name){
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("Rating");

        pq.whereEqualTo("RateTo", name);
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                //testName = e.toString();
                rater = 0;
                rating = 0;
                for (ParseObject po : list) {
                    rating = rating + (float) po.getDouble("rating");
                    rater++;
                }

                rating = rating / rater;
                RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingBar);
                ratingBar1.setRating(rating);
                TextView raters = (TextView) findViewById(R.id.raters);
                raters.setText("" + rater);
            }
        });
    }
}
