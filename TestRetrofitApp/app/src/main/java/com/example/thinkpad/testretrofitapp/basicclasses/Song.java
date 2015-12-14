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

    @SerializedName("playlist")
    public String playlist;

    public Song() {}

    public Song(long id,String songName, String artistName, String audioURL, String imageURL,String playlist) {
        this.id = id;
        this.songName = songName;
        this.artistName = artistName;
        this.audioURL = audioURL;
        this.imageURL = imageURL;
        this.playlist = playlist;
    }

    public Song(Song tmp){
        this.id = tmp.id;
        this.songName = tmp.songName;
        this.artistName = tmp.artistName;
        this.audioURL = tmp.audioURL;
        this.imageURL = tmp.imageURL;
        this.playlist = tmp.playlist;
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


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(artistName);
        str.append(" - " + songName);
        return str.toString();
    }
}

