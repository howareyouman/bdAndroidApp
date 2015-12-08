package com.example.thinkpad.testretrofitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;




public class DataBaseClient {

    private SQLiteDatabase database;
    private DataBaseSongs dbHelperSongs;
    private DataBaseGroups dbHelperGroups;

    private String[] allColumnsSongs = { DataBaseSongs.COLUMN_ID,
            DataBaseSongs.COLUMN_SONGNAME, DataBaseSongs.COLUMN_AUTHORNAME,
            DataBaseSongs.COLUMN_AUDIOURL, DataBaseSongs.COLUMN_IMAGEURL};

    private String[] allColumnsGroups = { DataBaseGroups.COLUMN_ID,
            DataBaseGroups.COLUMN_GROUPNAME, DataBaseGroups.COLUMN_IMAGEURL};

    public DataBaseClient(Context context) {
        dbHelperGroups = new DataBaseGroups(context);
        dbHelperSongs = new DataBaseSongs(context);
    }

    public void open() throws SQLException {
        database = dbHelperSongs.getWritableDatabase();
    }

    public void close() {
        dbHelperSongs.close();
        dbHelperGroups.close();
    }

    public boolean createGroup(Group group) {
        boolean isChanged = false;
        ContentValues values = new ContentValues();
        Cursor cursor = database.query(DataBaseGroups.TABLE_NAME, null,"id = "+ group.id, null, null, null, null);
        if(cursor.getCount() <= 0) {
            isChanged = true;
            values.put(DataBaseGroups.COLUMN_ID, group.id);
            values.put(DataBaseGroups.COLUMN_GROUPNAME, group.groupName);
            values.put(DataBaseGroups.COLUMN_IMAGEURL, group.imageURL);

            database.insert(dbHelperGroups.TABLE_NAME, null,
                    values);
        }
        return isChanged;
    }

    public boolean createSong(Song song) {
        boolean isChanged = false;
        ContentValues values = new ContentValues();
        Cursor cursor = database.query(DataBaseSongs.TABLE_NAME, null,"id = "+ song.id, null, null, null, null);
        if(cursor.getCount() <= 0) {
            isChanged = true;
            values.put(DataBaseSongs.COLUMN_ID, song.id);
            values.put(DataBaseSongs.COLUMN_SONGNAME, song.songName);
            values.put(DataBaseSongs.COLUMN_AUTHORNAME, song.artistName);
            values.put(DataBaseSongs.COLUMN_AUDIOURL, song.audioURL);
            values.put(DataBaseSongs.COLUMN_IMAGEURL, song.imageURL);

            database.insert(dbHelperSongs.TABLE_NAME, null,
                    values);
        }
        return isChanged;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBaseSongs.TABLE_NAME, allColumnsSongs, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Song comment = cursorToSong(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        Collections.reverse(comments);
        return comments;
    }

    private Song cursorToSong(Cursor cursor) {
        Song song = new Song();
        //song.setId(cursor.getLong(0));
        song.setSong(cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_SONGNAME)),
                cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_AUTHORNAME)),
                cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_AUDIOURL)),
                cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_IMAGEURL)));
        return song;
    }
}
