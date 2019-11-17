package com.example.gym.buddies.utils;

import android.content.Context;
import android.content.Intent;

public class IntentUtil {
    public static Intent getIntentForGymBuddies(Context parentActivity, Class activityToStart) {
       return new Intent(parentActivity, activityToStart);
    }
}
