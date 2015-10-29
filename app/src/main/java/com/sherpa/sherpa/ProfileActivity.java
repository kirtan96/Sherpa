package com.sherpa.sherpa;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final boolean[] av = {false};
        final boolean[] h = {true};

        Intent intent = getIntent();
        final ParseUser user = ParseUser.getCurrentUser();
        TextView name = (TextView) findViewById(R.id.name);
        final EditText city = (EditText) findViewById(R.id.city);
        final RadioButton avyes = (RadioButton) findViewById(R.id.avyes);
        final RadioButton avno = (RadioButton) findViewById(R.id.avno);
        final EditText places = (EditText) findViewById(R.id.places);
        final EditText cost = (EditText) findViewById(R.id.cost);
        final RadioButton hour = (RadioButton) findViewById(R.id.hourButton);
        final RadioButton day = (RadioButton) findViewById(R.id.dayButton);
        avyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avyes.setChecked(true);
                avno.setChecked(false);
                av[0] = true;
            }
        });
        avno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avyes.setChecked(false);
                avno.setChecked(true);
                av[0] = false;
            }
        });
        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour.setChecked(true);
                day.setChecked(false);
                h[0] = true;
            }
        });
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour.setChecked(false);
                day.setChecked(true);
                h[0] = false;
            }
        });
        Button save = (Button) findViewById(R.id.saveButton);
        name.setText(user.getString("firstname") + " " + user.getString("lastname"));
        if(user.getBoolean("available"))
        {
            avyes.setChecked(true);
            avno.setChecked(false);
            av[0] = true;
        }
        else
        {
            avyes.setChecked(false);
            avno.setChecked(true);
            av[0] = false;
        }
        city.setText(user.getString("gcity"));
        cost.setText(user.getDouble("cost") + "");
        if(user.getString("costType").equals("H"))
        {
            hour.setChecked(true);
            day.setChecked(false);
            h[0] = true;
        }
        else
        {
            hour.setChecked(false);
            day.setChecked(true);
            h[0] = false;
        }
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
                    user.put("available", av[0]);
                    user.put("profile", true);
                    if (h[0]) {
                        user.put("costType", "H");
                    } else {
                        user.put("costType", "D");
                    }
                    user.saveInBackground();
                    Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent1);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    builder.setMessage("Please fill everything in properly.")
                            .setTitle("Oops")
                            .setPositiveButton(R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }

}
