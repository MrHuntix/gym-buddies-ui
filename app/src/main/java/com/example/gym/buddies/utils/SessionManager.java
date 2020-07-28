package com.example.gym.buddies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.example.gym.buddies.data.model.jwtgen.UserLoginResponse;
import com.example.gym.buddies.data.protos.LoginSignupProto;
import com.google.protobuf.ByteString;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class SessionManager {
    private static final LoginSignupProto.LoginResponse LOGIN_RESPONSE = LoginSignupProto.LoginResponse.newBuilder().buildPartial();

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn, LoginSignupProto.LoginResponse response) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("logged_in_status", loggedIn);
        editor.putString("jwt_token", "dummy_jwt_token");
        editor.putString("user_name", response.getUserName());
        editor.putInt("user_id", response.getUserId());
        editor.putInt("pic_id", response.getPicId());
        setBytes(editor, response.getUserImage());
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

    public static LoginSignupProto.LoginResponse getLoginResponseForLogout() {
        return LOGIN_RESPONSE;
    }

    public static void logInfo(Context context) {
        Log.d("2", getPreferences(context).getString("jwt_token", null));
        Log.d("2", getPreferences(context).getString("user_name", null));
    }

    private static byte[] getBytes(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getPreferences(context).getString("image", null).getBytes(StandardCharsets.ISO_8859_1);
        }
        return Base64.decode(getPreferences(context).getString("image", null), Base64.DEFAULT);
    }

    private static void setBytes(SharedPreferences.Editor editor, ByteString bytes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            editor.putString("image", new String(bytes.toByteArray(), StandardCharsets.ISO_8859_1));
        }
        editor.putString("image", new String(bytes.toByteArray(), Base64.DEFAULT));
    }
}
