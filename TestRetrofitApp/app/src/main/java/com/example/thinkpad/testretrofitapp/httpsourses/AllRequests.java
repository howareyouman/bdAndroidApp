package com.example.thinkpad.testretrofitapp.httpsourses;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.POST;

public interface AllRequests {
    @POST("/")
    void postBePop(Callback<Response> callback);

}
