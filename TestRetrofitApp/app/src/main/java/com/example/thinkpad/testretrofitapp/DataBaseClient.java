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
    private DataBase dbHelper;

    private String[] allColumnsSongs = { DataBase.COLUMN_ID,
            DataBase.COLUMN_SONGNAME, DataBase.COLUMN_AUTHORNAME,
            DataBase.COLUMN_AUDIOURL, DataBase.COLUMN_IMAGEURL};

    private String[] allColumnsGroups = { DataBase.COLUMN_ID,
            DataBase.COLUMN_GROUPNAME, DataBase.COLUMN_IMAGEURL};

    private String[] allColumnsPlaylists = { DataBase.COLUMN_ID,
            DataBase.COLUMN_PLAYLIST_NAME};

    public DataBaseClient(Context context) {
        dbHelper = new DataBase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        dbHelper.onCreate(database);
    }

    public void close() {
        dbHelper.close();
    }

    public boolean createGroup(Group group) {
        boolean isChanged = false;
        ContentValues values = new ContentValues();
        Cursor cursor = database.query(DataBase.TABLE_NAME_GROUPS, null,"groupname = \" "+ group.groupName + "\"", null, null, null, null);
        if(cursor.getCount() <= 0) {
            isChanged = true;

            values.put(DataBase.COLUMN_ID, group.id);
            values.put(DataBase.COLUMN_GROUPNAME, group.groupName);
            values.put(DataBase.COLUMN_IMAGEURL, group.imageURL);

            database.insert(dbHelper.TABLE_NAME_GROUPS, null,
                    values);
        }
        return isChanged;
    }

    public boolean createSong(Song song) {
        boolean isChanged = false;
        ContentValues values = new ContentValues();
        Cursor cursor = database.query(DataBase.TABLE_NAME_SONGS, null,"songname = \" "+ song.songName + "\"", null, null, null, null);
        if(cursor.getCount() <= 0) {
            isChanged = true;
            values.put(DataBase.COLUMN_ID, song.id);
            values.put(DataBase.COLUMN_SONGNAME, song.songName);
            values.put(DataBase.COLUMN_AUTHORNAME, song.artistName);
            values.put(DataBase.COLUMN_AUDIOURL, song.audioURL);
            values.put(DataBase.COLUMN_IMAGEURL, song.imageURL);

            database.insert(dbHelper.TABLE_NAME_SONGS, null,
                    values);
        }
        return isChanged;
    }

    public boolean createPlaylist(Playlist playlist) {
        boolean isChanged = false;
        ContentValues values = new ContentValues();
        Cursor cursor = database.query(
                DataBase.TABLE_NAME_PLAYLISTS,
                null,
                DataBase.COLUMN_PLAYLIST_NAME + " = \""+ playlist.playListName + "\"",
                null, null, null, null);

        if(cursor.getCount() <= 0) {
            isChanged = true;
            values.put(DataBase.COLUMN_ID, playlist.id);
            values.put(DataBase.COLUMN_PLAYLIST_NAME, playlist.playListName);

            database.insert(dbHelper.TABLE_NAME_PLAYLISTS, null,
                    values);
        }
        return isChanged;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TABLE_NAME_SONGS, allColumnsSongs, null, null, null, null, null);

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

    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TABLE_NAME_GROUPS, allColumnsGroups, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Group comment = cursorToGroup(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        Collections.reverse(comments);
        return comments;
    }

    public ArrayList<Playlist> getAllPlaylists() {
        ArrayList<Playlist> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TABLE_NAME_PLAYLISTS, allColumnsPlaylists, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Playlist comment = cursorToPlaylist(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        Collections.reverse(comments);
        return comments;
    }

    private Playlist cursorToPlaylist(Cursor cursor) {
        Playlist playlist = new Playlist(
                cursor.getLong(cursor.getColumnIndex(DataBase.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_PLAYLIST_NAME))
        );
        return playlist;
    }

    private Group cursorToGroup(Cursor cursor) {
        Group group = new Group(
                cursor.getLong(cursor.getColumnIndex(DataBase.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_GROUPNAME)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_IMAGEURL))
        );
        return group;
    }

    private Song cursorToSong(Cursor cursor) {
        Song song = new Song(
                cursor.getLong(cursor.getColumnIndex(DataBase.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_SONGNAME)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_AUTHORNAME)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_AUDIOURL)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_IMAGEURL))
        );
        return song;
    }

    public boolean deletePlaylist(String playlistName){
        boolean is_changed = false;
        int tmp = database.delete(DataBase.TABLE_NAME_PLAYLISTS,DataBase.COLUMN_PLAYLIST_NAME + " = ?", new String[] { playlistName });
        if(tmp != 0)
            is_changed = true;
        return is_changed;
    }
}
