package com.example.thinkpad.testretrofitapp.basicclasses;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Арсений on 12/9/2015.
 */
@Parcel
public class Group {
    @SerializedName("aid")
    public long id;

    @SerializedName("title")
    public String groupName;

    @SerializedName("image_url")
    public String imageURL;

    public Group() {}

    public Group(long id,String songName, String imageURL) {
        this.id = id;
        this.groupName = songName;
        this.imageURL = imageURL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Group getGroup() {
        return this;
    }


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(" groupName : " + groupName);
        str.append(" imageURL : " + imageURL);
        return str.toString();
    }

}
