package com.example.thinkpad.testretrofitapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.basicclasses.Playlist;
import com.example.thinkpad.testretrofitapp.databases.DataBaseClient;

import java.util.ArrayList;

public class SelectPlaylistActivity extends AppCompatActivity {

    ArrayList<Playlist> playlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_playlist);
        DataBaseClient dataBase = new DataBaseClient(this);
        dataBase.open();
       playlists = dataBase.getAllPlaylists();

        if(playlists.size()  > 0) {
            ArrayList arrayStrings = new ArrayList<>();

            for (int i = 0; i < playlists.size(); i++)
                arrayStrings.add(playlists.get(i).toString());


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    R.layout.custom_textview, arrayStrings);

            ListView l = (ListView)findViewById(R.id.listForPlaylists);
            l.setAdapter(adapter);
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("playlist name",playlists.get(position).playListName);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });
        }
    }
}
