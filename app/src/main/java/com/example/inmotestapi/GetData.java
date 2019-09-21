package com.example.inmotestapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetData {

    @GET("/users")
    Call<List<User>> getAllUser();

    @GET("/users")
    Call<User> getSingleUser(
            @Field("login") String login
    );
}
