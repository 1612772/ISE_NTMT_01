package com.example.nguyenhuutu.convenientmenu.login;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class UserSession {
    //file name
    private final String userPref = "UserPreferences";
    //mode that other app can't read this file
    private final int mode = MODE_PRIVATE;

    //properties of the file
    private final String IS_LOGIN = "IsLogin";
    private final String USERNAME = "UserName";
    private final String PASSWORD = "Password";
    private Context _context;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(userPref, mode);
        editor = pref.edit();
    }

    //if user login then change values in pref file
    public void createUserLoginSession(String uName, String uPassword){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(USERNAME, uName);
        editor.putString(PASSWORD, uPassword);

        // commit changes
        editor.commit();
    }

    //to get all user information
    public HashMap<String, String> getUserDetails(){
        //Use hash map to store user credentials
        HashMap<String, String> userDetails = new HashMap<String, String>();

        // user name
        userDetails.put(USERNAME, pref.getString(USERNAME, ""));

        // return user
        return userDetails;
    }

    //to clear all data of user when logout
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    // Check for user login?
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
