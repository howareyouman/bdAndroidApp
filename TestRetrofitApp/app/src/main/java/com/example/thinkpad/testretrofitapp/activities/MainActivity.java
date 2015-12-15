package com.example.thinkpad.testretrofitapp.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.basicclasses.Group;
import com.example.thinkpad.testretrofitapp.basicclasses.Playlist;
import com.example.thinkpad.testretrofitapp.basicclasses.Song;
import com.example.thinkpad.testretrofitapp.databases.DataBaseClient;
import com.example.thinkpad.testretrofitapp.databases.DatabaseDump;
import com.example.thinkpad.testretrofitapp.fragments.GroupListFragment;
import com.example.thinkpad.testretrofitapp.fragments.PlaylistsListFragment;
import com.example.thinkpad.testretrofitapp.fragments.SongListFragment;
import com.example.thinkpad.testretrofitapp.fragments.ViewSongsFromEverywhereFragment;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    public ViewSongsFromEverywhereFragment vsf;


    public int what = 0;
    public int songId = 0;
    public int groupId = 0;
    public int playlistId = 0;
    public String nameOfWhat = null;

    public Song songToAdd = null;

    protected Drawer.Result drawerResult = null;
    private DataBaseClient dataBase;
    private  FragmentManager fm;
    private SongListFragment slf;
    private GroupListFragment glf;
    private PlaylistsListFragment plf;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateDrawer();

        dataBase = new DataBaseClient(this);
        dataBase.open();

        init();

        fm = getFragmentManager();

        slf = new SongListFragment();
        glf = new GroupListFragment();
        plf = new PlaylistsListFragment();
        vsf = new ViewSongsFromEverywhereFragment();



        fm.beginTransaction()
                .add(R.id.FlashBarLayout, plf, "playlists")
                .hide(plf)
                .add(R.id.FlashBarLayout,glf,"groups")
                .hide(glf)
                .add(R.id.FlashBarLayout,slf,"songs")
                .commit();
        currentFragment = slf;

        songId = dataBase.countAllSongs()+1;
        groupId = dataBase.countAllGroups()+1;
        playlistId = dataBase.countAllPlaylists()+1;
        exportToXML();

    }

    public void SwitchTo (Fragment fragment, String name, String val) {
        if (fragment.isVisible())
            return;
        FragmentTransaction t = fm.beginTransaction();

        // Make sure the next view is below the current one
        fragment.getView().bringToFront();
        // And bring the current one to the very top
        currentFragment.getView().bringToFront();

        // Hide the current fragment
        t.hide(currentFragment);
        if(!name.equals("")){
            Bundle b = new Bundle();
            b.putString(name, val);
            fragment.setArguments(b);
        }
        t.show(fragment);
        currentFragment = fragment;

        // You probably want to add the transaction to the backstack
        // so that user can use the back button
        t.addToBackStack(null);
        t.commit();
    }

    public void setNewFragment(Fragment fragment){
        FragmentTransaction t = fm.beginTransaction();
        t.add(R.id.FlashBarLayout, fragment, "");
        t.hide(currentFragment);
        t.show(fragment);
        currentFragment = fragment;
        t.commit();
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
                        new PrimaryDrawerItem().withName("All my groups").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_playlist).withIcon(FontAwesome.Icon.faw_bed).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withIdentifier(1),
                        new SecondaryDrawerItem().withName("Exit").withIcon(FontAwesome.Icon.faw_android)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        switch (position){
                            case 1:
                                slf = new SongListFragment();
                                setNewFragment(slf);
                                break;
                            case 2:
                                glf = new GroupListFragment();
                                setNewFragment(glf);
                                break;
                            case 3:
                                plf = new PlaylistsListFragment();
                                setNewFragment(plf);
                                break;
                            case 4:
                                Toast.makeText(MainActivity.this, "Settings!", Toast.LENGTH_SHORT).show();
                                break;
                            case 6:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/howareyouman/bdAndroidApp"));
                                startActivity(browserIntent);
                                break;
                            case 7:
                                openQuitDialog();
                                break;
                        }
                    }
                })
                .build();
    }
    private void init(){
        if(dataBase.getAllSongs().size() == 0) {
            dataBase.createSong(new Song(++songId, "Песня о друге", "Владимир Высоцкий", "http://bit.ly/1J8XvzN",
                    "http://cdn.static4.rtr-vesti.ru/vh/pictures/gallery/256/929.jpg", "MySongs"));
            dataBase.createSong(new Song(++songId, "All My Heart", "John Newman", "http://bit.ly/1RmeOVS", "http://i2.ytimg.com/vi/qdO2jKpFpw4/mqdefault.jpg", "MySongs"));
            dataBase.createSong(new Song(++songId, "Drowning Shadows", "Sam Smith", "http://bit.ly/1RmeOVS", "http://www.thegarden.com/content/dam/msg/eventImg2/SamSmith_2015_328x253.jpg/_jcr_content/renditions/SamSmith_2015_328x253.328.254.jpg", "MySongs"));
            dataBase.createSong(new Song(++songId, "-30", "Аквариум", "", "http://kvartalmuz.ru/wp-content/uploads/2015/07/post_thumb_pOlM01Muxh.jpg", "MySongs"));
        }

        if(dataBase.getAllGroups().size() == 0) {
            dataBase.createGroup(new Group(++groupId, "Group_1", ""));
            dataBase.createGroup(new Group(++groupId, "Group_2", ""));
            dataBase.createGroup(new Group(++groupId, "Group_3", ""));
            dataBase.createGroup(new Group(++groupId, "Group_4", ""));
        }

        if(dataBase.getAllPlaylists().size() == 0) {
            dataBase.createPlaylist(new Playlist(++playlistId, "Школьные хиты"));
            dataBase.createPlaylist(new Playlist(++playlistId, "Для тренировки"));
            dataBase.createPlaylist(new Playlist(++playlistId, "Русский рок"));
            dataBase.createPlaylist(new Playlist(++playlistId, "Танцевальные хиты"));
            dataBase.createPlaylist(new Playlist(++playlistId, "Зажигаем на работе"));
        }


    }

    public ArrayList<Song> getSongs(){
        return dataBase.getAllSongs();
    }

    public ArrayList<Song> getSongsByPlaylist(String playlist){
        return dataBase.getSongByPlaylist(playlist);
    }

    public void newSong(Song song){
        dataBase.createSong(song);
    }

    public ArrayList<Song> getSongsByGroup(){
        ArrayList<Song> values = new ArrayList<>();
        values.add(new Song(++songId,"Light and Sound","Luke Million","http://bit.ly/1RmeOVS","https://avatars.yandex.net/get-music-content/629cc99d.a.2506673-1/600x600",""));
        values.add(new Song(++songId,"The Good The Bad and The Crazy","Imany","http://bit.ly/1RmeOVS","https://avatars.yandex.net/get-music-content/d9dce70d.a.2818497-1/600x600",""));
        values.add(new Song(++songId,"Intoxicated","Martin Solveig","http://bit.ly/1RmeOVS","https://avatars.yandex.net/get-music-content/173a234b.a.2656353-1/600x600",""));
        values.add(new Song(++songId, "Papaoutai", "Stromae", "http://bit.ly/1RmeOVS", "https://avatars.yandex.net/get-music-content/6f4f0309.a.1565559-1/600x600", ""));
        return values;

    }

    private void exportToXML(){
        File sd = Environment.getExternalStorageDirectory();
        String path = sd + "/my_data.xml";

        DatabaseDump databaseDump = new DatabaseDump(dataBase.database, path);
        databaseDump.exportData();
    }



    public ArrayList<Group> getAllGroups(){
        return dataBase.getAllGroups();
    }
    public void addToAllSongs(Song song){
        song.playlist = "MySongs";
        song.id = ++songId;
        dataBase.createSong(song);
        SongListFragment s = new SongListFragment();
        setNewFragment(s);
    }

    public ArrayList<Playlist> getAllPlaylists(){
        return dataBase.getAllPlaylists();
    }

    public boolean newPlaylist(String name){
        return dataBase.createPlaylist(new Playlist(++playlistId, name));
    }

    public boolean deletePlaylist(String name){
        return dataBase.deletePlaylist(name);
    }

    public boolean deleteGroup(String name){
        return dataBase.deleteGroup(name);
    }

    public boolean deleteSong(Song song){
        return dataBase.deleteSong(song);
    }

    @Override
    public void onBackPressed(){}

    private void openQuitDialog() {
        if(drawerResult.isDrawerOpen()){
            drawerResult.closeDrawer();
        }
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
