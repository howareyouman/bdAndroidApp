package com.example.thinkpad.testretrofitapp.basicclasses;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Арсений on 12/12/2015.
 */
@Parcel
public class Playlist {
    @SerializedName("id")
    public long id;

    @SerializedName("title")
    public String playListName;

    public Playlist() {}

    public Playlist(long id,String name){
        this.id = id;
        this.playListName = name;
    }

    @Override
    public String toString() {
        return playListName;
    }
}
