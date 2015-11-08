package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.parse.*;
import com.sherpa.sherpa.Activites.ViewProfile;
import com.sherpa.sherpa.Activites.ViewSherpaProfile;
import com.sherpa.sherpa.R;

import java.util.List;

public class RatingSherpa extends AppCompatActivity  {
    ParseUser user;
    private Button submitButton;
    public RatingBar ratingSherpa;
    private TextView ratingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_sherpa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent RatingSherpaIntent = getIntent();
        final String s = RatingSherpaIntent.getStringExtra("username");

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", s);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                for (ParseUser users : list) {
                    if (users.getUsername().equals(s)) {
                        user = users;
                    }
                }
            }
        });


        submitButton = (Button) findViewById(R.id.submitButton);
        ratingSherpa = (RatingBar) findViewById(R.id.ratingSherpa);
        ratingText = (TextView) findViewById(R.id.ratingText);


        ratingSherpa.setOnRatingBarChangeListener(
                new OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar userRating, float rating, boolean fromUser) {
                        ratingText.setText("You Have Rated " + String.valueOf(rating) + " out of 5 stars!");
                    }
                }
        );

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.containsKey("rater")) {
                    double average = (ratingSherpa.getRating() + user.getDouble("rating")) / 2;
                    user.put("rating", average);
                    user.increment("rater", 1);
                } else {
                    user.put("rating", ratingSherpa.getRating());
                    user.put("rater", 1);
                }
                user.saveInBackground();
                finish();
            }
        });
    }

    public void update(float rating) {
        float average = ((float) user.get("rating") + rating) / 2;
        user.put("rating", average);
    }
}
