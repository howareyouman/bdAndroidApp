package com.example.thinkpad.testretrofitapp;

import retrofit.RestAdapter;

/**
 * Created by ThinkPad on 29.11.2015.
 */
public class ClientForHTTP {
    static private AllRequests client;
    private static final String servUrl = "http://192.168.1.193:1337";
    private ClientForHTTP(){}
    public static AllRequests sharedInstance(){
        if(client == null){
            RestAdapter what = new RestAdapter.Builder().setEndpoint(servUrl).build();
            client = what.create(AllRequests.class);
        }
        return client;
    }
}
