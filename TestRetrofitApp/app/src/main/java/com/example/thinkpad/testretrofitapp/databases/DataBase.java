package com.example.thinkpad.testretrofitapp.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Арсений on 12/12/2015.
 */
public class DataBase extends SQLiteOpenHelper {
    public static final String TABLE_NAME_GROUPS = "groups";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GROUPNAME = "groupname";
    public static final String COLUMN_IMAGEURL = "imageurl";

    public static final String TABLE_NAME_SONGS = "songs";
    public static final String COLUMN_SONGNAME = "songname";
    public static final String COLUMN_AUTHORNAME = "authorname";
    public static final String COLUMN_AUDIOURL = "audiourl";

    public static final String TABLE_NAME_PLAYLISTS = "playlists";
    public static final String COLUMN_PLAYLIST_NAME = "playlistname";
    private static final String DATABASE_NAME = "mybase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SONGS = "create table if not exists "
            + TABLE_NAME_SONGS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SONGNAME
            + " text not null, " + COLUMN_AUTHORNAME +  " text not null, "
            + COLUMN_AUDIOURL + " text not null, "+ COLUMN_PLAYLIST_NAME + " text not null, "
            + COLUMN_IMAGEURL + " text not null);";

    private static final String DATABASE_CREATE_GROUPS = "create table if not exists "
            + TABLE_NAME_GROUPS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_GROUPNAME
            + " text not null, " + COLUMN_IMAGEURL + " text not null);";

    private static final String DATABASE_CREATE_PLAYLISTS = "create table if not exists "
            + TABLE_NAME_PLAYLISTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_PLAYLIST_NAME + " text not null);";

    public DataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_SONGS);
        database.execSQL(DATABASE_CREATE_GROUPS);
        database.execSQL(DATABASE_CREATE_PLAYLISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
