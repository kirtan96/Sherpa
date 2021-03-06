package com.sherpa.sherpa.Activites;

/**
 * Created by Kirtan on 10/21/15.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.HelperClasses.SherpaProfile;
import com.sherpa.sherpa.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class SearchSherpa extends AppCompatActivity {
    ArrayList<String> userListString = null;
    ArrayList<SherpaProfile> userlist;
    SherpaProfile currentUser;
    Class loadedList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sherpa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final TextView availableNumber = (TextView) findViewById(R.id.availableNumber);

        currentUser = new SherpaProfile(ParseUser.getCurrentUser());

        currentUser.setOnline(true);
        currentUser.saveUser();

        try {
            loadedList = Class.forName("java.util.ArrayList");
            userListString = (ArrayList) loadedList.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        final EditText editText = (EditText) findViewById(R.id.searchText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    handled = true;


                        final ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
                        query.findInBackground(new FindCallback<ParseUser>() {
                            @Override
                            public void done(List<ParseUser> list, ParseException e) {
                                int i = 0;
                                int total = 0;
                                userlist = new ArrayList<>();
                                userListString = new ArrayList<>();
                                String searchCity = editText.getText().toString().toLowerCase().trim();
                                for (ParseUser u : list) {
                                    SherpaProfile user = new SherpaProfile(u);
                                    if (!user.equals(ParseUser.getCurrentUser()) &&
                                            user.hasCreatedProfile()) {

                                        if (user.getCity().toLowerCase().equals(searchCity) ||
                                                user.getPlaces().toLowerCase().contains(searchCity)) {
                                            total++;
                                            if (user.getAvailability()) {
                                                userlist.add(user);
                                                //i++;

                                                userListString.add(user.getFirstname() + " " +
                                                                user.getLastname() +
                                                                "\nCharge: $" +
                                                                user.getCost() +
                                                                "/" + user.getCostType() + "\n"
                                                );
                                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchSherpa.this,
                                                        android.R.layout.simple_list_item_1, userListString);
                                                listView.setAdapter(arrayAdapter);
                                            }
                                        }
                                    }
                                }
                                if (userListString.isEmpty()) {
                                    userListString.add("There are no Sherpas available in this city!");
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchSherpa.this,
                                            android.R.layout.simple_list_item_1, userListString);
                                    listView.setAdapter(arrayAdapter);
                                }
                                try {
                                    Method m = loadedList.getMethod("size", (Class[]) null);
                                    i = (int) m.invoke(userListString, null);
                                } catch (NoSuchMethodException e1) {
                                    e1.printStackTrace();
                                } catch (InvocationTargetException e1) {
                                    e1.printStackTrace();
                                } catch (IllegalAccessException e1) {
                                    e1.printStackTrace();
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
        if(!currentUser.isNull()) {
            currentUser.setOnline(false);
            currentUser.saveUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!currentUser.isNull()) {
            currentUser.setOnline(true);
            currentUser.saveUser();
        }
    }
}