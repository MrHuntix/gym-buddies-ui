package com.example.gym.buddies.data.client;

import com.example.gym.buddies.data.model.jwtgen.User;
import com.example.gym.buddies.data.model.jwtgen.UserLoginRequest;
import com.example.gym.buddies.data.model.jwtgen.UserLoginResponse;
import com.example.gym.buddies.data.model.jwtgen.UserSignupRequest;
import com.example.gym.buddies.data.model.jwtgen.UserSignupResponse;
import com.example.gym.buddies.data.model.match.ChatResponse;
import com.example.gym.buddies.data.model.match.Match;
import com.example.gym.buddies.data.model.match.MatchLookup;
import com.example.gym.buddies.data.model.match.MatchResponse;
import com.example.gym.buddies.data.model.operation.Branch;
import com.example.gym.buddies.data.model.operation.GymRegisterRequest;
import com.example.gym.buddies.data.model.operation.GymResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Gbuddies {
    @POST("/auth/signup/")
    Call<UserSignupResponse> signup(@Body UserSignupRequest request);

    @POST("/auth/login/")
    Call<UserLoginResponse> login(@Body UserLoginRequest request);

    @GET("/auth/id/{id}/")
    Call<User> getUserById(@Path("id") int id);

    @POST("/gym/fetch/")
    Call<GymResponse> fetch();

    @POST("/gym/register/gym/")
    Call<GymResponse> registerGym(@Body GymRegisterRequest gymRegisterRequest);

    @GET("/gym/coordinates/{branchId}/")
    Call<Branch> coordinates(@Path("branchId") int branchId);

    @PUT("/match/buddy/requester/{requesterId}/gym/{gymId}/branch/{branchId}/")
    Call<MatchResponse> addForLookup(@Path("requesterId") int requesterId, @Path("gymId") int gymId, @Path("branchId") int branchId);

    @PUT("/match/like/{matchLookupId}/by/{userId}/")
    Call<MatchResponse> like(@Path("matchLookupId") int matchLookupId, @Path("userId") int userId);

    @PUT("/match/dislike/{matchId}/")
    Call<MatchResponse> unmatch(@Path("matchId") int matchId);

    @GET("/match/all/{requesterId}/gym/{gymId}/branch/{branchId}/")
    Call<List<MatchLookup>> getSuitableMatches(@Path("requesterId") int requesterId, @Path("gymId") int gymId, @Path("branchId") int branchId);

    @GET("/match/derive/{requesterId}/")
    Call<List<MatchLookup>> deriveMatches(@Path("requesterId") int requesterId);

    @GET("/match/matched/{requesterId}/")
    Call<List<ChatResponse>> getMatched(@Path("requesterId") int requesterId);
}
