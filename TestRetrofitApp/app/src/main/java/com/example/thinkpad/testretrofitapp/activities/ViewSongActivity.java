package com.example.thinkpad.testretrofitapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.basicclasses.Song;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class ViewSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song);

        Song song = Parcels.unwrap(this.getIntent().getParcelableExtra("song"));
        ImageView im = (ImageView)findViewById(R.id.imageForPic);
        if(song.imageURL.length() > 0) {
            Picasso.with(getApplicationContext())
                    .load(song.imageURL)
                    .resize(1000, 1000)
                    .centerCrop()
                    .into(im);
        }

        TextView nameOfSong = (TextView)findViewById(R.id.nameOfSong);
        nameOfSong.setText(song.toString());

        TextView songUrl = (TextView)findViewById(R.id.urlForSong);
        songUrl.setText(song.audioURL);
    }

}
