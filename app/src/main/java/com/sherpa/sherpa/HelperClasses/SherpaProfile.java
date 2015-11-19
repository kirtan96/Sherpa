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

    ParseUser user;

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
        if(user == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void addProfilPic(ParseFile pFile) {
        user.put("pp", pFile);
    }

    @Override
    public void setProfileCreated(boolean b) {
        user.put("profile", b);
    }
}
