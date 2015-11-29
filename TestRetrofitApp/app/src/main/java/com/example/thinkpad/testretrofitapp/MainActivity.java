package com.example.thinkpad.testretrofitapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MainActivity extends AppCompatActivity {
    Client client;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.test);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.sharedInstance().postBePop(new Type("The Beatles - Yesterday"), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        String json = new String(((TypedByteArray) response.getBody()).getBytes());
                        text.setText(json);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        text.setText("Failure!");
                    }
                });

            }
        });
    }
}
