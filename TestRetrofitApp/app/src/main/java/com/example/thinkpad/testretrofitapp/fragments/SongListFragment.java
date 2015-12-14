package com.example.thinkpad.testretrofitapp.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.activities.MainActivity;
import com.example.thinkpad.testretrofitapp.activities.SelectPlaylistActivity;
import com.example.thinkpad.testretrofitapp.activities.ViewSongActivity;
import com.example.thinkpad.testretrofitapp.basicclasses.Song;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Арсений on 12/11/2015.
 */
public class SongListFragment extends ListFragment {
    MainActivity activity;
    private ArrayList<String> arrayStrings;
    private ArrayList<Song> arraySongs;
    private int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        activity = (MainActivity) getActivity();

        arraySongs =  activity.getSongs();
        arrayStrings= new ArrayList<>();

        for(int i=0;i<arraySongs.size();i++)
            arrayStrings.add(arraySongs.get(i).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.custom_textview, arrayStrings);

        setListAdapter(adapter);
        registerForContextMenu(view);
        return view;

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Show");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Add to playlist");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Show"){
            if(pos != 0) {
                Intent intent = new Intent(getActivity(), ViewSongActivity.class);
                intent.putExtra("song", Parcels.wrap(arraySongs.get(pos)));
                startActivity(intent);
            }
        } else if(item.getTitle()=="Add to playlist"){
            Intent intent = new Intent(getActivity(), SelectPlaylistActivity.class);
            startActivityForResult(intent,1);
        }else if(item.getTitle()=="Delete"){
            if(activity.deleteSong(arraySongs.get(pos)))
                upgrade();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("playlist name");

                Song tmp = new Song(arraySongs.get(pos));
                tmp.id = ++activity.songId;
                tmp.playlist = result;

                //activity.addToAllSongs(tmp);
                activity.newSong(tmp);

                activity.what = 2;
                activity.nameOfWhat = result;
                ViewSongsFromEverywhereFragment v = new ViewSongsFromEverywhereFragment();
                activity.setNewFragment(v);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void upgrade(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity a = getActivity();
        pos = position;
        a.openContextMenu(v);
    }
}
