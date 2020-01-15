package com.example.gym.buddies.data.wrappers;

import com.example.gym.buddies.data.model.GymsResponse;
import com.google.gson.Gson;

public class GymWrapper {

    public static GymsResponse getGymResponseFromJson(String json) {
        return new Gson().fromJson(json, GymsResponse.class);
    }
}
