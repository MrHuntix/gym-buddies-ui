package com.example.gym.buddies.data.wrappers;

import com.example.gym.buddies.data.model.operation.GymResponse;
import com.google.gson.Gson;

public class GymWrapper {

    public static GymResponse getGymResponseFromJson(String json) {
        return new Gson().fromJson(json, GymResponse.class);
    }
}
