package com.example.thinkpad.testretrofitapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.basicclasses.Song;

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
