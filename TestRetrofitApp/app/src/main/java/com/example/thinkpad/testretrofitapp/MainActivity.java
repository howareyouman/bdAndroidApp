package com.example.thinkpad.testretrofitapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private ClientForHTTP clientForHTTP;
    private TextView text;

    protected Drawer.Result drawerResult = null;

    static boolean isActive = false;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Song> songsList;

    private ClientForHTTP client;
    private DataBaseClient dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateDrawer();
        TextView text = (TextView) findViewById(R.id.textSongs);
        text.setText("All Songs");
        dataBase = new DataBaseClient(this);
        dataBase.open();

        songsList = new ArrayList<>();

        getSongsFromBase();
        initAdapter();
        getSongsFromInternet();

    }

    private void getSongsFromBase() {
        songsList.addAll(dataBase.getAllSongs());
    }

    private void initAdapter() {
    }

    public void updateView() {
        songsList.clear();
        getSongsFromBase();
        initAdapter();
        mAdapter.notifyDataSetChanged();
    }

    private void getSongsFromInternet() {
        /*client.sharedInstance().getWhat(new SortedList.Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String json = new String(((TypedByteArray) response.getBody()).getBytes());

                try {
                    JSONObject obj = new JSONObject(json);
                    JSONArray for_array = obj.getJSONArray("bepopular");
                    Gson parser = new Gson();
                    ArrayList<Song> array = new ArrayList<Song>(Arrays.asList(parser.fromJson(for_array.toString(), Song[].class)));
                    boolean isChanged = false;
                    for (Song song : array) {
                        if(dataBase.createSong(song))
                            isChanged = true;
                    }
                    if(isChanged)
                        updateView();
                } catch (Exception e) {
                    Log.e("", e.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("", error.toString());
            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause () {
        super.onPause();
    }


    protected void onCreateDrawer(){
        drawerResult = new Drawer()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_mysongs).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_groups).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_playlist).withIcon(FontAwesome.Icon.faw_bed).withIdentifier(1),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withIdentifier(1)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        switch (position){
                            case 1:
                                //Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                                //intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //startActivity(intent1);
                                //Toast.makeText(MainActivity.this, MainActivity.this.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                                //onBackPressed();
                                break;
                            case 2:
                                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                break;
                            case 3:
                                Toast.makeText(MainActivity.this, "Playlists!", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(MainActivity.this, "Settings!", Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                Toast.makeText(MainActivity.this, "default!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .build();
    }
    @Override
    public void onBackPressed(){
        if(drawerResult.isDrawerOpen()){
            drawerResult.closeDrawer();
        }
        openQuitDialog();
    }
    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Да, спасибо!", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        quitDialog.show();
    }
}
