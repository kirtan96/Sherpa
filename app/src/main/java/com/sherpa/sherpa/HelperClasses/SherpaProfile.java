package com.sherpa.sherpa.HelperClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.sherpa.sherpa.Interfaces.Profile;

/**
 * Created by Kirtan on 10/30/15.
 */
public class SherpaProfile implements Profile{

    private ParseUser user;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String city;
    private double cost;
    private boolean availability;
    private String costType;
    private String places;

    public SherpaProfile(ParseUser u)
    {

        user = u;
        firstname = user.getString("firstname");
        lastname = user.getString("lastname");
        email = user.getEmail();
        phone = user.getString("phone");
        cost = user.getDouble("cost");
        availability = user.getBoolean("available");
        costType = user.getString("costType");
        city = user.getString("gcity");
        places = user.getString("places");
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public Boolean getAvailability() {
        return availability;
    }

    @Override
    public String getCostType() {
        return costType;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getPlaces() {
        return places;
    }

    @Override
    public void setEmail(String email1) {
        email = email1;
        user.setEmail(email);
    }

    @Override
    public void setPhone(String phone1) {
        phone = phone1;
        user.put("phone", phone);
    }

    @Override
    public void setCost(Double cost1) {
        cost = cost1;
        user.put("cost", cost);
    }

    @Override
    public void setAvailability(Boolean availability1) {
        availability = availability1;
        user.put("available", true);
    }

    @Override
    public void setCostType(String costType1) {
        costType = costType1;
        user.put("costType", costType);
    }

    @Override
    public void setProfilePic(final ImageView profilePic)
    {
        ParseFile file = user.getParseFile("pp");
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                profilePic.setImageBitmap(bmp);
            }
        });
    }

    @Override
    public void setCity(String city1) {
        city = city1;
        user.put("gcity", city);
    }

    @Override
    public void setPlaces(String places1) {
        places = places1;
        user.put("places", places);
    }

    @Override
    public void saveUser() {
        user.saveInBackground();
    }

    @Override
    public Boolean contains(String s) {
        return user.containsKey(s);
    }

    @Override
    public void setChattingWith(String s) {
        user.put("chattingWith", s);
    }

    @Override
    public void setOnline(boolean b) {
        user.put("online", b);
    }

    @Override
    public void setLoggedIn(boolean b) {
        user.put("loggedIn", true);
    }

    @Override
    public boolean hasCreatedProfile() {
        return user.getBoolean("profile");
    }

    @Override
    public boolean isNull() {
        return user == null;
    }

    @Override
    public void addProfilPic(ParseFile pFile) {
        user.put("pp", pFile);
    }

    @Override
    public void setProfileCreated(boolean b) {
        user.put("profile", b);
    }

    @Override
    public boolean getTransportation() {
        return user.getBoolean("transportation");
    }

    @Override
    public int getPassengers() {
        return user.getInt("passengers");
    }

    @Override
    public void setTransportation(boolean transportation) {
        user.put("transportation", transportation);
    }

    @Override
    public void setPassengers(int passengers) {
        user.put("passengers", passengers);
    }

    @Override
    public String getTransportationString() {
        String s;
        if(getTransportation())
        {
            s = "Transportation: upto " + (getPassengers()-1) + " people";
        }
        else
        {
            s = "Transportation: none";
        }
        return s;
    }


}