package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.R;

import java.util.List;

public class RatingSherpa extends AppCompatActivity  {

    private Button submitButton;
    public RatingBar ratingSherpa;
    private TextView ratingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_sherpa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitButton = (Button) findViewById(R.id.submitButton);
        ratingSherpa = (RatingBar) findViewById(R.id.ratingSherpa);
        ratingText = (TextView) findViewById(R.id.ratingText);
        ratingText.setText("Rate the Sherpa!");

        Intent intent = getIntent();
        final String buddy = intent.getStringExtra("username");


        final Intent RatingSherpaIntent = getIntent();
        final String s = RatingSherpaIntent.getStringExtra("username");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                for (ParseUser users : list)
                    if (users.getUsername().equals(s)) {
                        users.saveInBackground();
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
                                submitRating(buddy);
                            }
                        });
                    }
            }
        });
    }

    private void submitRating(String buddy) {
        ParseObject po = new ParseObject("Rating");
        po.put("RateFrom", ParseUser.getCurrentUser().getUsername());
        po.put("RateTo", buddy);
        po.put("rating", ratingSherpa.getRating());
        po.saveEventually();
        finish();
    }
}
