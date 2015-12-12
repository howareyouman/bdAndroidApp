package com.example.thinkpad.testretrofitapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.parceler.Parcels;

public class ViewSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song);

        Song song = Parcels.unwrap(this.getIntent().getParcelableExtra("song"));
        TextView text = (TextView)findViewById(R.id.textSong);
        text.setText(song.toString());
    }

}
