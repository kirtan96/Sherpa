package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.sherpa.sherpa.Chat;
import com.sherpa.sherpa.R;

import java.util.List;

/**
 * Created by Kirtan on 10/21/15.
 */

public class ViewSherpaProfile extends AppCompatActivity {

    ParseUser user;
    String sherpaName;
    float rating;
    int rater;
    RatingBar ratingBar;
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

        user = ParseUser.getCurrentUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                for (ParseUser u : list) {
                    if (u.getUsername().equals(username)) {
                        user = u;

                        final ImageView profilePic = (ImageView) findViewById(R.id.profilePicture);
                        TextView name = (TextView) findViewById(R.id.name);
                        TextView city = (TextView) findViewById(R.id.city);
                        TextView placeCity = (TextView) findViewById(R.id.placeCity);
                        TextView places = (TextView) findViewById(R.id.listOfPlaces);
                        TextView cost = (TextView) findViewById(R.id.cost);
                        TextView email = (TextView) findViewById(R.id.email);
                        email.setText(user.getEmail());
                        TextView phone = (TextView) findViewById(R.id.phone);
                        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                        phone.setText(user.getString("phone"));

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
                        if (user.getString("costType").equals("H")) {
                            hour = "hour";
                        } else {
                            hour = "day";
                        }
                        cost.setText(cost.getText() + "$" + user.getDouble("cost") + "/" + hour);
                        Button chatButton = (Button) findViewById(R.id.chatButton);
                        chatButton.setText("Chat with " + user.getString("firstname") + " " + user.getString("lastname"));
                        chatButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navigateToChat();
                            }
                        });

                        Button rateSherpa = (Button) findViewById(R.id.rateSherpa);
                        rateSherpa.setText("Rate " + user.getString("firstname") + " " + user.getString("lastname"));
                        rateSherpa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navigateToRate();
                            }
                        });

                        updateRating(u.getUsername());


                    }
                }
            }
        });


    }

    private void navigateToRate() {
        Intent intent = new Intent(ViewSherpaProfile.this, RatingSherpa.class);
        intent.putExtra("username", sherpaName);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.checkout, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_checkout) {

            //navigateToCheckout();
        //}


        return super.onOptionsItemSelected(item);
    }

    private void navigateToChat()
    {
        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("username", sherpaName);
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
        updateRating(getIntent().getStringExtra("username"));
    }

    public void updateRating(String name){
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("Rating");
        pq.whereEqualTo("RateTo", name);
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
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
