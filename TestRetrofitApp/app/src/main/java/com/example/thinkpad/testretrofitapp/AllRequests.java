package com.example.thinkpad.testretrofitapp;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface AllRequests {
    @POST("/")
    void postBePop(@Body Type type, Callback<Response> callback);

}
