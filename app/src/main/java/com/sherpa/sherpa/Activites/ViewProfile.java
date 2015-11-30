package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.R;
import com.sherpa.sherpa.UserList;

import java.util.List;

/**
 * Created by Kirtan on 10/21/15.
 */

public class ViewProfile extends AppCompatActivity {

    int rater;
    float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button chatButton = (Button) findViewById(R.id.current_chats);

        ParseUser user = ParseUser.getCurrentUser();
        user.put("online", true);
        user.saveInBackground();
        final ImageView profilePic = (ImageView) findViewById(R.id.profilePicture);
        TextView name = (TextView) findViewById(R.id.name);
        TextView city = (TextView) findViewById(R.id.city);
        TextView placeCity = (TextView) findViewById(R.id.placeCity);
        TextView places = (TextView) findViewById(R.id.listOfPlaces);
        TextView cost = (TextView) findViewById(R.id.cost);
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail());
        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(user.getString("phone"));
        Button edit  = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToEditProfile();
            }
        });

        ParseFile file = user.getParseFile("pp");
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                profilePic.setImageBitmap(bmp);
            }
        });
        name.setText(user.getString("firstname") + " " + user.getString("lastname"));
        city.setText(city.getText() + user.getString("gcity"));
        placeCity.setText(placeCity.getText() + user.getString("gcity") + ":");
        places.setText(user.getString("places"));
        String hour;
        if(user.getString("costType").equals("H"))
        {
            hour = "hour";
        }
        else
        {
            hour = "day";
        }
        cost.setText(cost.getText() + "$" + user.getDouble("cost") + "/" + hour);

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
                rater = 0;
                rating = 0;
                if(list!=null) {
                    for (ParseObject po : list) {
                        rating = rating + (float) po.getDouble("rating");
                        rater++;
                    }
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
