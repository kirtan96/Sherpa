package com.sherpa.sherpa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final ParseUser user = ParseUser.getCurrentUser();
        TextView name = (TextView) findViewById(R.id.name);
        final EditText city = (EditText) findViewById(R.id.city);
        Button save = (Button) findViewById(R.id.saveButton);
        name.setText(user.getString("firstname") + " " + user.getString("lastname"));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.put("gcity", city.getText());
                user.saveInBackground();
            }
        });

    }

}
