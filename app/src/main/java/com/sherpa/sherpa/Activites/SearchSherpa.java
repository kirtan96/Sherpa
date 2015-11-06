package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kirtan on 10/21/15.
 */

public class SearchSherpa extends AppCompatActivity {
    ArrayList<String> userListString;
    ArrayList<ParseUser> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sherpa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final TextView availableNumber = (TextView) findViewById(R.id.availableNumber);

        ParseUser.getCurrentUser().put("online", true);
        ParseUser.getCurrentUser().saveInBackground();

        final EditText editText = (EditText) findViewById(R.id.searchText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;


                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            int i = 0;
                            int total = 0;
                            userlist = new ArrayList<>();
                            userListString = new ArrayList<>();
                            for (ParseUser user : list) {
                                if (user.getString("gcity").toLowerCase().equals(editText.getText().toString().toLowerCase()) ||
                                        user.getString("places").toLowerCase().contains(editText.getText().toString().toLowerCase())) {
                                    total++;
                                    if (user.getBoolean("available")) {
                                        userlist.add(user);
                                        i++;
                                        userListString.add(user.getString("firstname") + " " + user.getString("lastname"));
                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchSherpa.this,
                                                android.R.layout.simple_list_item_1, userListString);
                                        listView.setAdapter(arrayAdapter);
                                    }
                                }
                            }
                            if(userListString.isEmpty())
                            {
                                userListString.add("There are no Sherpas available in this city!");
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchSherpa.this,
                                        android.R.layout.simple_list_item_1, userListString);
                                listView.setAdapter(arrayAdapter);
                            }
                            availableNumber.setText(i + " of " + total + " Sherpas available");
                        }
                    });

                }
                return handled;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = listView.getItemAtPosition(position).toString();
                if(!name.equals("There are no Sherpas available in this city!") && !name.isEmpty())
                {
                    Intent intent = new Intent(SearchSherpa.this, ViewSherpaProfile.class);
                    intent.putExtra("username", userlist.get(position).getUsername());
                    startActivity(intent);
                }
            }
        });
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
    }
}
