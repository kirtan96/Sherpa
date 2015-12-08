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


public class SherpaProfile implements Profile{

    private ParseUser user;

    public SherpaProfile(ParseUser u)
    {
        user = u;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getFirstname() {
        return user.getString("firstname");
    }

    @Override
    public String getLastname() {
        return user.getString("lastname");
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getPhone() {
        return user.getString("phone");
    }

    @Override
    public Double getCost() {
        return user.getDouble("cost");
    }

    @Override
    public Boolean getAvailability() {
        return user.getBoolean("available");
    }

    @Override
    public String getCostType() {
        return user.getString("costType");
    }

    @Override
    public String getCity() {
        return user.getString("gcity");
    }

    @Override
    public String getPlaces() {
        return user.getString("places");
    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public void setPhone(String phone) {
        user.put("phone", phone);
    }

    @Override
    public void setCost(Double cost) {
        user.put("cost", cost);
    }

    @Override
    public void setAvailability(Boolean availability) {
        user.put("available", true);
    }

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

    @Override
    public void setCity(String city) {
        user.put("gcity", city);
    }

    @Override
    public void setPlaces(String places) {
        user.put("places", places);
    }

    /**
     * saves the user in the background
     */
    @Override
    public void saveUser() {
        user.saveInBackground();
    }

    /**
     * if the backend contains the key
     * @param s the key to be searched in the backend
     * @return true or false
     */
    @Override
    public Boolean contains(String s) {
        return user.containsKey(s);
    }

    /**
     * Sets the chattig with to the provided string
     * @param s string to be put in the chattingWith key in the backend
     */
    @Override
    public void setChattingWith(String s) {
        user.put("chattingWith", s);
    }

    /**
     * Set the online status of the user
     * @param b true or false based on whether or not the user is online
     */
    @Override
    public void setOnline(boolean b) {
        user.put("online", b);
    }

    /**
     * Sets the users logged in
     * @param b true or false based on whether or not the user is logged in
     */
    @Override
    public void setLoggedIn(boolean b) {
        user.put("loggedIn", true);
    }

    /**
     * Checks if the user has created tour guide profile
     * @return true or false
     */
    @Override
    public boolean hasCreatedProfile() {
        return user.getBoolean("profile");
    }

    /**
     * Checks if the user is null or not
     * @return true or false
     */
    @Override
    public boolean isNull() {
        return user == null;
    }

    /**
     * Adds the profile picture to the user
     * @param pFile the profile picture
     */
    @Override
    public void addProfilPic(ParseFile pFile) {
        user.put("pp", pFile);
    }

    /**
     * puts true or false based on whether or not the user has created the tour guide profile
     * @param b true or false
     */
    @Override
    public void setProfileCreated(boolean b) {
        user.put("profile", b);
    }

    /**
     * Gets the availability of the user's avaialbility on transportation
     * @return True or false based on the availability
     */
    @Override
    public boolean getTransportation() {
        return user.getBoolean("transportation");
    }

    /**
     * Gets the number of passengers that the user can provide transportation to
     * @return the number of the passengers
     */
    @Override
    public int getPassengers() {
        return user.getInt("passengers");
    }

    /**
     * Sets the transportation availability of the user
     * @param transportation true or false based on user's availability
     */
    @Override
    public void setTransportation(boolean transportation) {
        user.put("transportation", transportation);
    }

    /**
     * Sets the availability of the passengers that the user can give ride to
     * @param passengers number of passengers
     */
    @Override
    public void setPassengers(int passengers) {
        user.put("passengers", passengers);
    }

    /**
     * Gets the sentence of the user's transportation avaialability
     * @return the full sentence
     */
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