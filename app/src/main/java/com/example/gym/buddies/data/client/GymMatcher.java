package com.example.gym.buddies.data.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GymMatcher {

    @PUT("/buddy/requester/{requesterId}/gym/{gymId}/branch/{branchId}/")
    Call<String> addForLookup(@Header("Authorization") String authHeader, @Path("requesterId") int requesterId, @Path("gymId") int gymId, @Path("branchId") int branchId);

    @PUT("like/{matchLookupId}/by/{userId}/")
    Call<String> like(@Header("Authorization") String authHeader, @Path("matchLookupId") int matchLookupId, @Path("userId") int userId);

    @PUT("/dislike/{matchId}/")
    Call<String> unmatch(@Header("Authorization") String authHeader, @Path("matchId") int matchId);

    @GET("/all/{requesterId}/gym/{gymId}/branch/{branchId}/")
    Call<String> getSuitableMatches(@Header("Authorization") String authHeader, @Path("requesterId") int requesterId, @Path("gymId") int gymId, @Path("branchId") int branchId);
}
