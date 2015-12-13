package com.example.thinkpad.testretrofitapp.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.basicclasses.Group;
import com.example.thinkpad.testretrofitapp.basicclasses.Playlist;
import com.example.thinkpad.testretrofitapp.basicclasses.Song;
import com.example.thinkpad.testretrofitapp.databases.DataBaseClient;
import com.example.thinkpad.testretrofitapp.fragments.GroupListFragment;
import com.example.thinkpad.testretrofitapp.fragments.PlaylistsListFragment;
import com.example.thinkpad.testretrofitapp.fragments.SongListFragment;
import com.example.thinkpad.testretrofitapp.fragments.ViewSongsFromEverywhereFragment;
import com.example.thinkpad.testretrofitapp.httpsourses.ClientForHTTP;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    static boolean isActive = false;
    public ViewSongsFromEverywhereFragment vsf;
    public int what = 0;
    public String nameOfWhat = null;
    protected Drawer.Result drawerResult = null;
    private ClientForHTTP clientForHTTP;
    private TextView text;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Song> songsList;
    private ClientForHTTP client;
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

        fm = getFragmentManager();

        slf = new SongListFragment();
        glf = new GroupListFragment();
        plf = new PlaylistsListFragment();
        vsf = new ViewSongsFromEverywhereFragment();
        /**/

        fm.beginTransaction()
                .add(R.id.FlashBarLayout, plf, "playlists")
                .hide(plf)
                .add(R.id.FlashBarLayout,glf,"groups")
                .hide(glf)
                .add(R.id.FlashBarLayout,slf,"songs")
                .commit();
        currentFragment = slf;



        dataBase = new DataBaseClient(this);
        dataBase.open();

    }

    private void getSongsFromBase() {
        songsList.addAll(dataBase.getAllSongs());
    }

    public void SwitchTo (Fragment fragment, String name, String val)
    {
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
        t.add(R.id.FlashBarLayout,fragment,"");
        t.hide(currentFragment);
        t.show(fragment);
        currentFragment = fragment;
        t.commit();
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
                        new PrimaryDrawerItem().withName("All my groups").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(2),
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
                                SwitchTo(slf,"","");
                                break;
                            case 2:
                                SwitchTo(glf,"","");
                                break;
                            case 3:
                                SwitchTo(plf,"","");
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

    public ArrayList<Song> getSongs(){
        ArrayList<Song> values;

        dataBase.createSong(new Song(1,"V","1","",""));
        dataBase.createSong(new Song(2, "No_Way_Out", "2", "", ""));
        dataBase.createSong(new Song(3, "Army_of_Noise", "3", "", ""));
        dataBase.createSong(new Song(4, "Skin", "5", "", ""));

        values = dataBase.getAllSongs();

        return values;

    }

    public ArrayList<Song> getSongsByPlaylist(){
        ArrayList<Song> values = new ArrayList<>();
        values.add(new Song(1, "123", "", "", ""));
        values.add(new Song(2, "234", "", "", ""));
        values.add(new Song(3, "345", "", "", ""));
        values.add(new Song(4,"456","","",""));
        return values;

    }

    public ArrayList<Song> getSongsByGroup(){
        ArrayList<Song> values = new ArrayList<>();
        values.add(new Song(1,"1","","",""));
        values.add(new Song(2,"2","","",""));
        values.add(new Song(3,"3","","",""));
        values.add(new Song(4,"4","","",""));
        return values;

    }

    public ArrayList<Group> getAllGroups(){
        ArrayList<Group> arrayList;
        dataBase.createGroup(new Group(1,"Group_1",""));
        dataBase.createGroup(new Group(2, "Group_2", ""));
        dataBase.createGroup(new Group(3, "Group_3", ""));
        dataBase.createGroup(new Group(4, "Group_4", ""));

        arrayList = dataBase.getAllGroups();

        return arrayList;
    }

    public ArrayList<Playlist> getAllPlaylists(){
        ArrayList<Playlist> arrayList;
        //dataBase.createPlaylist(new Playlist(1, "For sleep"));
        //dataBase.createPlaylist(new Playlist(2, "For run"));
        //dataBase.createPlaylist(new Playlist(3, "For work"));

        arrayList = dataBase.getAllPlaylists();
        return arrayList;
    }

    public boolean newPlaylist(String name){
        int n = (dataBase.getAllPlaylists()).size();
        return dataBase.createPlaylist(new Playlist(n+1,name));
    }

    public boolean deletePlaylist(String name){
        return dataBase.deletePlaylist(name);
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
