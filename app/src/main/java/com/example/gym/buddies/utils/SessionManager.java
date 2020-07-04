package com.example.gym.buddies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.gym.buddies.data.model.jwtgen.UserLoginResponse;

import java.util.HashSet;
import java.util.Set;

public class SessionManager {
    private static final UserLoginResponse LOGIN_RESPONSE = new UserLoginResponse();

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn, UserLoginResponse response) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("logged_in_status", loggedIn);
        editor.putString("jwt_token", response.getJwtToken());
        editor.putString("user_name", response.getUserName());
        editor.putInt("user_id", response.getUserId());
        editor.apply();
    }

    public static void setGymBranch(Context context, int gymId, int branchId) {
        Set<String> lookupSet = getPreferences(context).getStringSet("gym_branch_pairs", null);
        SharedPreferences.Editor editor = getPreferences(context).edit();
        if(lookupSet==null) {
            lookupSet = new HashSet<>();
            lookupSet.add(gymId + "-" + branchId);
            editor.putStringSet("gym_branch_pairs", lookupSet);
            editor.apply();
            Log.d("logTag", "created new lookup set. Added pair: " + gymId + "-" + branchId);
            return;
        }
        editor.putStringSet("gym_branch_pairs", lookupSet);
        editor.apply();
        Log.d("logTag", "Added pair to existing lookup set: " + gymId + "-" + branchId);
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

    public static int getUserId(Context context) {
        return getPreferences(context).getInt("user_id", -1);
    }

    public static Set<String> getGymBranch(Context context) {
        return getPreferences(context).getStringSet("gym_branch_pairs", null);
    }

    public static UserLoginResponse getLoginResponseForLogout() {
        return LOGIN_RESPONSE;
    }

    public static void logInfo(Context context) {
        Log.d("2", getPreferences(context).getString("jwt_token", null));
        Log.d("2", getPreferences(context).getString("user_name", null));
    }
}
