package com.example.thinkpad.testretrofitapp;

/**
 * Created by Арсений on 12/1/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseSongs extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "song";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SONGNAME = "songname";
    public static final String COLUMN_AUTHORNAME = "authorname";
    public static final String COLUMN_AUDIOURL = "audiourl";
    public static final String COLUMN_IMAGEURL = "imageurl";


    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SONGNAME
            + " text not null, " + COLUMN_AUTHORNAME +  " text not null, "
            + COLUMN_AUDIOURL + " text not null, "+ COLUMN_IMAGEURL + " text not null);";

    public DataBaseSongs(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataBaseSongs.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
