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
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.sherpa.sherpa.R;
import com.sherpa.sherpa.UserList;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button chatButton = (Button) findViewById(R.id.current_chats);

        ParseUser user = ParseUser.getCurrentUser();
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
    }

    private void navigateToChatList() {
        Intent intent = new Intent(ViewProfile.this, UserList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_profile) {
            navigateToEditProfile();
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToEditProfile() {
        Intent intent = new Intent(ViewProfile.this, EditProfileActivity.class);
        startActivity(intent);
    }

}
