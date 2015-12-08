package com.example.thinkpad.testretrofitapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Арсений on 12/9/2015.
 */
public class DataBaseGroups extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "groups";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GROUPNAME = "groupname";
    public static final String COLUMN_IMAGEURL = "imageurl";


    private static final String DATABASE_NAME = "mybase.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_GROUPNAME
            + " text not null, " + COLUMN_IMAGEURL + " text not null);";

    public DataBaseGroups(Context context) {
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
