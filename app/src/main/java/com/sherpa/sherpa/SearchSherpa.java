package com.sherpa.sherpa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchSherpa extends AppCompatActivity {
    ArrayList<String> userListString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sherpa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView listView = (ListView) findViewById(R.id.listView);

        final EditText editText = (EditText) findViewById(R.id.searchText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    final ArrayList<ParseUser> userlist = new ArrayList<>();


                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            userListString = new ArrayList<>();
                            for (ParseUser user : list) {
                                if (user.getString("gcity").toLowerCase().equals(editText.getText().toString().toLowerCase())) {
                                    if (user.getBoolean("available")) {
                                        userlist.add(user);
                                        userListString.add(user.getString("firstname") + " " + user.getString("lastname"));
                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchSherpa.this,
                                                android.R.layout.simple_list_item_1, userListString);
                                        listView.setAdapter(arrayAdapter);
                                    }
                                }
                            }
                            if(userListString.isEmpty())
                            {
                                userListString.add("There are no Sherpas available in this city!");
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchSherpa.this,
                                        android.R.layout.simple_list_item_1, userListString);
                                listView.setAdapter(arrayAdapter);
                            }
                        }
                    });

                }
                return handled;
            }
        });
    }

}
