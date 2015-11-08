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
import android.widget.Toast;

import com.parse.*;
import com.sherpa.sherpa.Activites.ViewProfile;
import com.sherpa.sherpa.Activites.ViewSherpaProfile;
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

        Intent intent = getIntent();
        final String buddy = intent.getStringExtra("username");


        final Intent RatingSherpaIntent = getIntent();
        final String s = RatingSherpaIntent.getStringExtra("username");

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        //query.whereEqualTo("username", s);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                for (ParseUser users : list)
                    if (users.getUsername().equals(s)) {
                        final ParseUser user = users;

                        user.saveInBackground();
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
                                khatam(buddy);

                            }
                        });
                    }
            }
        });


        submitButton = (Button) findViewById(R.id.submitButton);
        ratingSherpa = (RatingBar) findViewById(R.id.ratingSherpa);
        ratingText = (TextView) findViewById(R.id.ratingText);


    }

    private void khatam(String buddy) {
        ParseObject po = new ParseObject("Rating");
        po.put("RateFrom", ParseUser.getCurrentUser().getUsername());
        po.put("RateTo", buddy);
        // po.put("createdAt", "");
        po.put("rating", ratingSherpa.getRating());
        po.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(RatingSherpa.this, "your text", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RatingSherpa.this, "yourtext", Toast.LENGTH_LONG).show();
                }
            }
        });
        finish();
    }






}
