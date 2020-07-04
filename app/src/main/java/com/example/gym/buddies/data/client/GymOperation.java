package com.example.gym.buddies.data.client;

import com.example.gym.buddies.data.model.operation.GymRegisterRequest;
import com.example.gym.buddies.data.model.operation.GymResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GymOperation {

    @GET("/all/gyms/")
    Call<GymResponse> getAllGyms(@Header("Authorization") String authHeader);

    @POST("/register/gym/")
    Call<GymResponse> registerGym(@Header("Authorization") String authHeader, @Body GymRegisterRequest gymRegisterRequest);
}
