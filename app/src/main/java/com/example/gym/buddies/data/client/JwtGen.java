package com.example.gym.buddies.data.client;

import com.example.gym.buddies.data.model.jwtgen.UserLoginRequest;
import com.example.gym.buddies.data.model.jwtgen.UserLoginResponse;
import com.example.gym.buddies.data.model.jwtgen.UserSignupRequest;
import com.example.gym.buddies.data.model.jwtgen.UserSignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JwtGen {
    @POST("/signup")
    Call<UserSignupResponse> signup(@Body UserSignupRequest request);

    @POST("/login")
    Call<UserLoginResponse> login(@Body UserLoginRequest request);
}
