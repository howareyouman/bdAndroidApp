package com.example.thinkpad.testretrofitapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thinkpad.testretrofitapp.basicclasses.Song;
import com.example.thinkpad.testretrofitapp.basicclasses.Group;
import com.example.thinkpad.testretrofitapp.basicclasses.Playlist;

import java.util.ArrayList;
import java.util.Collections;




public class DataBaseClient {

    public SQLiteDatabase database;
    private DataBase dbHelper;

    private String[] allColumnsSongs = { DataBase.COLUMN_ID,
            DataBase.COLUMN_SONGNAME, DataBase.COLUMN_AUTHORNAME,
            DataBase.COLUMN_AUDIOURL, DataBase.COLUMN_IMAGEURL, DataBase.COLUMN_PLAYLIST_NAME};

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
        Cursor cursor = database.query(DataBase.TABLE_NAME_SONGS, null,"songname = \""+ song.songName + "\" and " + "playlistname = \""+ song.playlist+ "\"", null, null, null, null);
        if(cursor.getCount() <= 0) {
            isChanged = true;
            values.put(DataBase.COLUMN_ID, song.id);
            values.put(DataBase.COLUMN_SONGNAME, song.songName);
            values.put(DataBase.COLUMN_AUTHORNAME, song.artistName);
            values.put(DataBase.COLUMN_AUDIOURL, song.audioURL);
            values.put(DataBase.COLUMN_IMAGEURL, song.imageURL);
            values.put(DataBase.COLUMN_PLAYLIST_NAME,song.playlist);

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
        Cursor cursor = database.query(DataBase.TABLE_NAME_SONGS, null, "playlistname = \""+ "MySongs"+ "\"", null, null, null, null);

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

    public ArrayList<Song> getSongByPlaylist(String playlist){
        ArrayList<Song> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TABLE_NAME_SONGS, null,DataBase.COLUMN_PLAYLIST_NAME + " = \""+ playlist + "\"" , null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Song comment = cursorToSong(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        Collections.reverse(comments);
        return  comments;
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

    public int countAllSongs(){
        ArrayList<Song> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TABLE_NAME_SONGS, allColumnsSongs,null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Song comment = cursorToSong(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        Collections.reverse(comments);
        return  comments.size();
    }

    public int countAllGroups(){
        ArrayList<Group> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TABLE_NAME_GROUPS, allColumnsGroups,null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Group comment = cursorToGroup(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        Collections.reverse(comments);
        return  comments.size();
    }

    public int countAllPlaylists(){
        ArrayList<Playlist> comments = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TABLE_NAME_PLAYLISTS, allColumnsPlaylists,null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Playlist comment = cursorToPlaylist(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        Collections.reverse(comments);
        return  comments.size();
    }

    private Song cursorToSong(Cursor cursor) {
        Song song = new Song(
                cursor.getLong(cursor.getColumnIndex(DataBase.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_SONGNAME)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_AUTHORNAME)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_AUDIOURL)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_IMAGEURL)),
                cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_PLAYLIST_NAME))
        );
        return song;
    }

    public boolean deletePlaylist(String playlistName){
        boolean isChanged = false;
        int fromTablePlaylists = database.delete(DataBase.TABLE_NAME_PLAYLISTS,DataBase.COLUMN_PLAYLIST_NAME + " =?", new String[] { playlistName });
        int fromTableSongs = database.delete(DataBase.TABLE_NAME_SONGS,DataBase.COLUMN_PLAYLIST_NAME + " =?",new String[] { playlistName });
        if(fromTablePlaylists != 0 || fromTableSongs != 0)
            isChanged = true;
        return isChanged;
    }

    public boolean deleteGroup(String groupName){
        boolean isChanged = false;
        int fromTableGroups = database.delete(DataBase.TABLE_NAME_GROUPS,DataBase.COLUMN_GROUPNAME + " = ?",new String[]{groupName});
        if(fromTableGroups >0)
            isChanged = true;
        return isChanged;
    }

    public boolean deleteSong(Song song){
        boolean isChanged = false;
        int fromTableGroups = database.delete(DataBase.TABLE_NAME_SONGS, DataBase.COLUMN_SONGNAME + "=\"" + song.songName+"\""+
                       " and " + DataBase.COLUMN_AUTHORNAME + "=\"" +song.artistName+"\"" +
                        " and " + DataBase.COLUMN_PLAYLIST_NAME + "=\"" + song.playlist+"\""
                ,null);

        if(fromTableGroups > 0)
            isChanged = true;
        return isChanged;
    }
}
