package com.example.thinkpad.testretrofitapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ThinkPad on 29.11.2015.
 */
public class Type {
    @SerializedName("q")
    String str;
    public Type(String s){
        this.str = s;
    }
}
