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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.HelperClasses.SherpaProfile;
import com.sherpa.sherpa.R;
import com.sherpa.sherpa.UserList;

import java.util.List;



public class ViewProfile extends AppCompatActivity {

    private int rater;
    private float rating;
    private SherpaProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button chatButton = (Button) findViewById(R.id.current_chats);

        user = new SherpaProfile(ParseUser.getCurrentUser());
        user.setAvailability(true);
        user.saveUser();
        final ImageView profilePic = (ImageView) findViewById(R.id.profilePicture);
        TextView name = (TextView) findViewById(R.id.name);
        TextView city = (TextView) findViewById(R.id.city);
        TextView places = (TextView) findViewById(R.id.listOfPlaces);
        TextView cost = (TextView) findViewById(R.id.cost);
        TextView email = (TextView) findViewById(R.id.email);
        TextView transportation = (TextView) findViewById(R.id.transportation);
        transportation.setText(user.getTransportationString());
        email.setText(user.getEmail());
        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(user.getPhone());
        Button edit  = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToEditProfile();
            }
        });

        user.setProfilePic(profilePic);
        name.setText(user.getFirstname() + " " + user.getLastname());
        city.setText(city.getText() + user.getCity());
        places.setText(user.getPlaces());
        String hour;
        if(user.getCostType().equals("H"))
        {
            hour = "hour";
        }
        else
        {
            hour = "day";
        }
        cost.setText(cost.getText() + "$" + user.getCost() + "/" + hour);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToChatList();
            }
        });
        updateRating(ParseUser.getCurrentUser().getUsername());
    }

    private void navigateToChatList() {
        Intent intent = new Intent(ViewProfile.this, UserList.class);
        startActivity(intent);
    }

    private void navigateToEditProfile() {
        Intent intent = new Intent(ViewProfile.this, EditProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().put("online", false);
            ParseUser.getCurrentUser().saveInBackground();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().put("online", true);
            ParseUser.getCurrentUser().saveInBackground();
        }
        updateRating(ParseUser.getCurrentUser().getUsername());
    }

    public void updateRating(String name){
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("Rating");
        pq.whereEqualTo("RateTo", name);
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                setRater(0);
                setRating(0);
                if(list!=null) {
                    for (ParseObject po : list) {
                        setRating(getRating() + (float) po.getDouble("rating"));
                        setRater(getRater() + 1);
                    }
                }
                setRating(getRating() / getRater());
                RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingBar);
                ratingBar1.setRating(rating);
                TextView raters = (TextView) findViewById(R.id.raters);
                raters.setText("" + getRater());
            }
        });
    }

    public int getRater(){
        return this.rater;
    }

    public void setRater(int rater1){
        rater = rater1;
    }

    public float getRating(){
        return rating;
    }

    public void setRating(float rating1){
        rating = rating1;
    }
}
