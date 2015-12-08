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

    /**
     * Sets the profile picture of the user
     * @param profilePic the ImageView to set the profile pic to
     */
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

    /**
     * compares two users
     * @param o user to be compared with
     * @return true if they are same or else false
     */
    @Override
    public boolean equals(Object o) {

        if(o == this)
        {
           return true;
        }
        if(o == null || o.getClass() != this.getClass())
        {
            return false;
        }
        SherpaProfile user2 = (SherpaProfile) o;
        return getUsername().equals(user2.getUsername());
    }
}