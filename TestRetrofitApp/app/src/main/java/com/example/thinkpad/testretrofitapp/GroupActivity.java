package com.example.thinkpad.testretrofitapp;

import android.os.Bundle;
import android.widget.TextView;


public class GroupActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        super.onCreateDrawer();
        TextView text = (TextView) findViewById(R.id.textGroups);
        text.setText("All Groups");
    }

    @Override
    public void onBackPressed(){
        super.finish();
    }



}
