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
    @Override
    public ParseUser getCurrentParseUser() {
        return ParseUser.getCurrentUser();
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
    public void getProfilePic(final ImageView view) {
        if(user.containsKey("pp"))
        {
            ParseFile file = user.getParseFile("pp");
            file.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    view.setImageBitmap(bmp);
                }
            });
        }
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
    public void setCurrentParseUser() {
        this.user = ParseUser.getCurrentUser();
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
    public void setProfilePic(ParseFile file) {
        user.put("pp", file);
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
}
