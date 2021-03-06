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
import com.example.gym.buddies.data.protos.GymProto;
import com.example.gym.buddies.data.protos.LoginSignupProto;
import com.example.gym.buddies.data.protos.MatchLookupProto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Gbuddies {
    @POST("/auth/signup/")
    Call<LoginSignupProto.SignupResponse> signup(@Body LoginSignupProto.SignupRequest request);

    @POST("/auth/login/")
    Call<LoginSignupProto.LoginResponse> login(@Body LoginSignupProto.LoginRequest request);

    @GET("/auth/id/{id}/")
    Call<LoginSignupProto.LoginResponse> getUserById(@Path("id") int id);

    @POST("/gym/fetch/")
    Call<GymProto.FetchResponse> fetch();

    @POST("/gym/register/gym/")
    Call<GymProto.RegisterResponse> registerGym(@Body GymRegisterRequest gymRegisterRequest);

    @GET("/gym/coordinates/{branchId}/")
    Call<GymProto.CoordinateResponse> coordinates(@Path("branchId") int branchId);

    @PUT("/match/buddy/requester/{requesterId}/gym/{gymId}/branch/{branchId}/")
    Call<MatchLookupProto.MatchResponse> addForLookup(@Path("requesterId") int requesterId, @Path("gymId") int gymId, @Path("branchId") int branchId);

    @PUT("/match/like/{matchLookupId}/by/{userId}/")
    Call<MatchLookupProto.MatchResponse> like(@Path("matchLookupId") int matchLookupId, @Path("userId") int userId);

    @PUT("/match/reject/{matchRequestId}/")
    Call<MatchLookupProto.MatchResponse> reject(@Path("matchRequestId") int matchRequestId);

    @GET("/match/all/{requesterId}/gym/{gymId}/branch/{branchId}/")
    Call<MatchLookupProto.LookupResponse> getSuitableMatches(@Path("requesterId") int requesterId, @Path("gymId") int gymId, @Path("branchId") int branchId);

    @GET("/match/derive/{requesterId}/")
    Call<MatchLookupProto.LookupResponse> deriveMatches(@Path("requesterId") int requesterId);

    @GET("/match/friends/{userId}/")
    Call<MatchLookupProto.FriendResponse> getFriends(@Path("userId") int userId);

    @GET("/match/requests/{requesterId}/")
    Call<MatchLookupProto.FriendRequestsResponse> getFriendRequests(@Path("requesterId") int requesterId);

    @PUT("/match/accept/{matchRequestId}/")
    Call<MatchLookupProto.MatchResponse> acceptFriendRequest(@Path("matchRequestId") int matchRequestId);
}
