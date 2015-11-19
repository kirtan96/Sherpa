package com.sherpa.sherpa.Interfaces;

import android.widget.ImageView;

import com.parse.ParseFile;

/**
 * Created by Kirtan on 10/30/15.
 */
public interface Profile {
    String getUsername();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPhone();
    Double getCost();
    Boolean getAvailability();
    String getCostType();
    String getCity();
    String getPlaces();
    void setEmail(String email);
    void setPhone(String phone);
    void setCost(Double cost);
    void setAvailability(Boolean availability);
    void setCostType(String costType);
    void setProfilePic(ImageView file);
    void setCity(String city);
    void setPlaces(String places);
    void saveUser();
    Boolean contains(String s);
    void setChattingWith(String s);
    void setOnline(boolean b);
    void setLoggedIn(boolean b);
    boolean hasCreatedProfile();
    boolean isNull();
    void addProfilPic(ParseFile pFile);
    void setProfileCreated(boolean b);
}
