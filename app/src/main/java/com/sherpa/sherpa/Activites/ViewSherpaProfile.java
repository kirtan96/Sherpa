package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.Chat;
import com.sherpa.sherpa.R;

import java.util.List;

public class ViewSherpaProfile extends AppCompatActivity {

    ParseUser user;
    String sherpaName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sherpa_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        sherpaName = username;

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
                    }
                }
            }
        });
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
        if (id == R.id.action_checkout) {

            navigateToCheckout();
        }


        return super.onOptionsItemSelected(item);
    }

    private void navigateToCheckout()
    {
        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("username", sherpaName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
