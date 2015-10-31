package com.sherpa.sherpa.Interfaces;

import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * Created by Kirtan on 10/30/15.
 */
public interface Profile {
    ParseUser getCurrentParseUser();
    String getUsername();
    String getFirstname();
     String getLastname();
     String getEmail();
     String getPhone();
     Double getCost();
     Boolean getAvailability();
     String getCostType();
     void getProfilePic(ImageView view);
     String getCity();
     String getPlaces();
     void setCurrentParseUser();
     void setEmail(String email);
     void setPhone(String phone);
     void setCost(Double cost);
     void setAvailability(Boolean availability);
     void setCostType(String costType);
     void setProfilePic(ParseFile file);
     void setCity(String city);
     void setPlaces(String places);
     void saveUser();
     Boolean contains(String s);
}
