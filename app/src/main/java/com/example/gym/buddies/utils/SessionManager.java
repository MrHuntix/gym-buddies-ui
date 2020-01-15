package com.example.gym.buddies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.gym.buddies.data.model.LoginResponse;

public class SessionManager {
    private static final LoginResponse LOGIN_RESPONSE = new LoginResponse();

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn, LoginResponse response) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("logged_in_status", loggedIn);
        editor.putString("jwt_token", response.getJwtToken());
        editor.putString("user_name", response.getUserName());
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean("logged_in_status", false);
    }

    public static String getJwtToken(Context context) {
        return getPreferences(context).getString("jwt_token", null);
    }

    public static String getUsername(Context context) {
        return getPreferences(context).getString("user_name", null);
    }

    public static LoginResponse getLoginResponseForLogout() {
        return LOGIN_RESPONSE;
    }
}
