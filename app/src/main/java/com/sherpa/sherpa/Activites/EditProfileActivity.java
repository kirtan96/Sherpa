package com.sherpa.sherpa.Activites;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.sherpa.sherpa.HelperClasses.SherpaProfile;
import com.sherpa.sherpa.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Kirtan on 10/21/15.
 */

public class EditProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private static final int PICK_FROM_GALLERY = 2;
    SherpaProfile user;
    boolean av;
    boolean tr;
    boolean h;
    int passengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = new SherpaProfile(ParseUser.getCurrentUser());
        user.setOnline(true);
        user.saveUser();
        profilePic = (ImageView) findViewById(R.id.profilePic);
        TextView name = (TextView) findViewById(R.id.name);
        final EditText city = (EditText) findViewById(R.id.city);
        final RadioButton avyes = (RadioButton) findViewById(R.id.avyes);
        final RadioButton avno = (RadioButton) findViewById(R.id.avno);
        final RadioButton tryes = (RadioButton) findViewById(R.id.tryes);
        final RadioButton trno = (RadioButton) findViewById(R.id.trno);
        final EditText places = (EditText) findViewById(R.id.places);
        final EditText cost = (EditText) findViewById(R.id.cost);
        final RadioButton hour = (RadioButton) findViewById(R.id.hourButton);
        final RadioButton day = (RadioButton) findViewById(R.id.dayButton);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Integer[] pass = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,14, 15, 16, 17, 18, 19,20};
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, pass);
        spinner.setAdapter(integerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                passengers = (int)parent.getItemAtPosition(position) + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        tr = true;
        if(user.contains("pp")) {
            user.setProfilePic(profilePic);
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
        tryes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryes.setChecked(true);
                trno.setChecked(false);
                tr = true;

            }
        });
        trno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryes.setChecked(false);
                trno.setChecked(true);
                tr = false;
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
        name.setText(user.getFirstname() + " " + user.getLastname());
        if(user.getAvailability())
        {
            avyes.setChecked(true);
            avno.setChecked(false);
            av = true;
        } else {
            avyes.setChecked(false);
            avno.setChecked(true);
            av = false;
        }
        if(user.getTransportation())
        {
            tryes.setChecked(true);
            trno.setChecked(false);
            tr = true;
            spinner.setSelection(user.getPassengers()-2);
        }
        else
        {
            tryes.setChecked(false);
            trno.setChecked(true);
            tr = false;
        }
        city.setText(user.getCity());
        cost.setText(user.getCost() + "");
        if(user.getCostType().equals("H"))
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
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 0);
                    intent.putExtra("aspectY", 0);
                    intent.putExtra("outputX", 170);
                    intent.putExtra("outputY", 150);
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);

                } catch (ActivityNotFoundException e) {

                }
            }
        });

        places.setText(user.getPlaces());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.contains("pp") &&
                        city.getText().toString().length() > 1 &&
                        places.getText().toString().length() > 1 &&
                        !city.getText().toString().contains("-") &&
                        !city.getText().toString().contains(",") &&
                        !city.getText().toString().contains(";") &&
                        !city.getText().toString().contains("/") &&
                        Double.parseDouble(cost.getText().toString()) > 0) {
                    user.setCity(city.getText().toString());
                    user.setTransportation(tr);
                    if(tr)
                    {
                        user.setPassengers(passengers);
                    }
                    user.setCost(Double.parseDouble(cost.getText().toString()));
                    user.setPlaces(places.getText().toString());
                    user.setAvailability(av);
                    user.setProfileCreated(true);
                    user.setPhone(phone.getText().toString());
                    user.setEmail(email.getText().toString());
                    if (h) {
                        user.setCostType("H");
                    } else {
                        user.setCostType("D");
                    }
                    user.saveUser();
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
        if(resultCode==RESULT_OK) {
            if (requestCode == PICK_FROM_GALLERY) {
                Bundle extras2 = data.getExtras();
                if (extras2 != null) {
                    Bitmap photo = extras2.getParcelable("data");
                    profilePic.setImageBitmap(photo);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    ParseFile pFile = new ParseFile(user.getUsername() + ".jpg", stream.toByteArray());
                    pFile.saveInBackground();
                    user.addProfilPic(pFile);
                    user.saveUser();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!user.isNull()) {
            user.setOnline(false);
            user.saveUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!user.isNull()) {
            user.setOnline(true);
            user.saveUser();
        }
    }
}
