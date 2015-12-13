package com.example.thinkpad.testretrofitapp.basicclasses;

/**
 * Created by Арсений on 12/1/2015.
 */
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Song {
    @SerializedName("aid")
    public long id;

    @SerializedName("title")
    public String songName;

    @SerializedName("artist")
    public String artistName;

    @SerializedName("url")
    public String audioURL;

    @SerializedName("image_url")
    public String imageURL;

    public Song() {}

    public Song(long id,String songName, String artistName, String audioURL, String imageURL) {
        this.id = id;
        this.songName = songName;
        this.artistName = artistName;
        this.audioURL = audioURL;
        this.imageURL = imageURL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Song getSong() {
        return this;
    }

    public void setSong(String name,String author,String auURL,String imURL) {
        this.songName = name;
        this.artistName = author;
        this.audioURL = auURL;
        this.imageURL = imURL;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(artistName);
        str.append(" - " + songName);
        return str.toString();
    }
}

