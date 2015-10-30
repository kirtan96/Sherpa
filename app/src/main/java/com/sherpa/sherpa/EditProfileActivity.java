package com.sherpa.sherpa;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private static final int PICK_FROM_GALLERY = 2;
    ParseUser user;
    boolean av;
    boolean h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //final boolean[] av = {false};
        //final boolean[] h = {true};

        final Intent activityintent = getIntent();
        user = ParseUser.getCurrentUser();
        TextView insImage =(TextView) findViewById(R.id.textView);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        TextView name = (TextView) findViewById(R.id.name);
        final EditText city = (EditText) findViewById(R.id.city);
        final RadioButton avyes = (RadioButton) findViewById(R.id.avyes);
        final RadioButton avno = (RadioButton) findViewById(R.id.avno);
        final EditText places = (EditText) findViewById(R.id.places);
        final EditText cost = (EditText) findViewById(R.id.cost);
        final RadioButton hour = (RadioButton) findViewById(R.id.hourButton);
        final RadioButton day = (RadioButton) findViewById(R.id.dayButton);

        if(user.containsKey("pp")) {
            ParseFile file = user.getParseFile("pp");
            file.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    profilePic.setImageBitmap(bmp);
                }
            });
        }
        avyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avyes.setChecked(true);
                avno.setChecked(false);
                av = true;
            }
        });
        avno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avyes.setChecked(false);
                avno.setChecked(true);
                av = false;
            }
        });
        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour.setChecked(true);
                day.setChecked(false);
                h = true;
            }
        });
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour.setChecked(false);
                day.setChecked(true);
                h = false;
            }
        });
        Button save = (Button) findViewById(R.id.saveButton);
        name.setText(user.getString("firstname") + " " + user.getString("lastname"));
        if(user.getBoolean("available"))
        {
            avyes.setChecked(true);
            avno.setChecked(false);
            av = true;
        } else {
            avyes.setChecked(false);
            avno.setChecked(true);
            av = false;
        }
        city.setText(user.getString("gcity"));
        cost.setText(user.getDouble("cost") + "");
        if(user.getString("costType").equals("H"))
        {
            hour.setChecked(true);
            day.setChecked(false);
            h = true;
        }
        else
        {
            hour.setChecked(false);
            day.setChecked(true);
            h = false;
        }

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent intent = new Intent();
// call android default gallery
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
// ******** code for crop image
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 0);
                    intent.putExtra("aspectY", 0);
                    intent.putExtra("outputX", 200);
                    intent.putExtra("outputY", 150);
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);

                } catch (ActivityNotFoundException e) {

                }
            }
        });

        places.setText(user.getString("places"));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.getText().toString().length() > 1 &&
                        places.getText().toString().length() > 1 &&
                        !city.getText().toString().contains("-") &&
                        !city.getText().toString().contains(",") &&
                        !city.getText().toString().contains(";") &&
                        !city.getText().toString().contains("/") &&
                        Double.parseDouble(cost.getText().toString()) > 0) {
                    user.put("gcity", city.getText().toString());
                    user.put("cost", Double.parseDouble(cost.getText().toString()));
                    user.put("places", places.getText().toString());
                    user.put("available", av);
                    user.put("profile", true);
                    if (h) {
                        user.put("costType", "H");
                    } else {
                        user.put("costType", "D");
                    }
                    user.saveInBackground();
                    Intent intent1 = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(intent1);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                    builder.setMessage("Please fill everything in properly.")
                            .setTitle("Oops")
                            .setPositiveButton(R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FROM_GALLERY) {
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap photo = extras2.getParcelable("data");
                profilePic.setImageBitmap(photo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                ParseFile pFile = new ParseFile( user.getUsername() + ".jpg", stream.toByteArray());
                pFile.saveInBackground();
                user.put("pp", pFile);
            }
        }
    }
}
