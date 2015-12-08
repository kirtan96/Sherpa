package com.sherpa.sherpa.HelperClasses;

/**
 * Created by Kirtan on 10/30/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.sherpa.sherpa.Interfaces.Profile;

/**
 * It has all the information for the user
 */
public class SherpaProfile implements Profile{

    private ParseUser user;

    /**
     * initializes a new user
     * @param u the user
     */
    public SherpaProfile(ParseUser u)
    {
        user = u;
    }

    /**
     * gets the user's username
     * @return the username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * gets the user's first name
     * @return the first name
     */
    @Override
    public String getFirstname() {
        return user.getString("firstname");
    }

    /**
     * gets the user's last name
     * @return the last name
     */
    @Override
    public String getLastname() {
        return user.getString("lastname");
    }

    /**
     * gets the user's email id
     * @return the email id
     */
    @Override
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * gets the user's phone number
     * @return the phone number
     */
    @Override
    public String getPhone() {
        return user.getString("phone");
    }

    /**
     * gets the cost for a specific tour guide
     * @return the cost
     */
    @Override
    public Double getCost() {
        return user.getDouble("cost");
    }

    /**
     * gets the avaibility of a tour guide
     * @return the avaibility
     */
    @Override
    public Boolean getAvailability() {
        return user.getBoolean("available");
    }

    /**
     * gets the cost type ex. hourly/dailly
     * @return the type of cost
     */
    @Override
    public String getCostType() {
        return user.getString("costType");
    }

    /**
     * gets the city for tour
     * @return the city
     */
    @Override
    public String getCity() {
        return user.getString("gcity");
    }

    /**
     * gets the places the tour guide can give a tour for
     * @return the places
     */
    @Override
    public String getPlaces() {
        return user.getString("places");
    }

    /**
     * sets the email for the user
     * @param email the email
     */
    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    /**
     * sets the phone number for a user
     * @param phone the phone number
     */
    @Override
    public void setPhone(String phone) {
        user.put("phone", phone);
    }

    /**
     * sets the cost for a tour guide
     * @param cost the cost
     */
    @Override
    public void setCost(Double cost) {
        user.put("cost", cost);
    }

    /**
     * sets the avaibility for a tour guide
     * @param availability true if available or else false
     */
    @Override
    public void setAvailability(Boolean availability) {
        user.put("available", true);
    }

    /**
     * sets the cost of a User (as a Sherpa) per hour or day.
     * @param costType the cost type
     */
    @Override
    public void setCostType(String costType) {
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

    /**
     * sets the city for tour for a tour guide
     * @param city the city name
     */
    @Override
    public void setCity(String city) {
        user.put("gcity", city);
    }

    /**
     * sets the places for tour in a city
     * @param places the names of places
     */
    @Override
    public void setPlaces(String places) {
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