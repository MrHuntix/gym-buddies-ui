package com.example.gym.buddies.data.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A factory class that has various objects that are required to make api calls
 */
public class ApiFactory {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(30000, TimeUnit.SECONDS)
            .readTimeout(30000,TimeUnit.SECONDS)
            .writeTimeout(30000,TimeUnit.SECONDS);

    private static final String BASE_URL = "https://gbuddies.herokuapp.com/";
    /**
     * object to connect with jwt-auth service
     */
    public static Retrofit auth = new Retrofit.Builder()
            .baseUrl("https://jwt-gen.herokuapp.com/")
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * object to connect with gym-operation service
     */
    public static Retrofit operation = new Retrofit.Builder()
            .baseUrl("http://192.168.0.2:8765/operation/")
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * object to connect with gym-matcher service
     */
    public static Retrofit matcher = new Retrofit.Builder()
            .baseUrl("http://192.168.0.2:8765/match/")
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * object to connect with gbuddies service
     */
    public static Retrofit gbuddies = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
