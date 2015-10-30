package com.sherpa.sherpa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseUser user = ParseUser.getCurrentUser();
        final ImageView profilePic = (ImageView) findViewById(R.id.profilePicture);
        TextView name = (TextView) findViewById(R.id.name);
        TextView city = (TextView) findViewById(R.id.city);
        TextView placeCity = (TextView) findViewById(R.id.placeCity);
        TextView places = (TextView) findViewById(R.id.listOfPlaces);
        TextView cost = (TextView) findViewById(R.id.cost);

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
        String hour = "";
        if(user.getString("costType").equals("H"))
        {
            hour = "hour";
        }
        else
        {
            hour = "day";
        }
        cost.setText(cost.getText() + "$" + user.getDouble("cost") + "/" + hour);
    }

}
