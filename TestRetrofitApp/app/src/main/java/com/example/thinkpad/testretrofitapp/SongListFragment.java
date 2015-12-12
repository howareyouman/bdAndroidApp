package com.example.thinkpad.testretrofitapp;

import android.app.Activity;
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

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Арсений on 12/11/2015.
 */
public class SongListFragment extends ListFragment {
    private ArrayList<String> arrayStrings;
    private ArrayList<Song> arraySongs;
    private int pos = 0;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        MainActivity activity = (MainActivity) getActivity();

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
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Show"){
            if(pos != 0) {
                Intent intent = new Intent(getActivity(), ViewSongActivity.class);
                intent.putExtra("song", Parcels.wrap(arraySongs.get(pos)));
                startActivity(intent);
            }
        }
        else if(item.getTitle()=="Add to playlist"){

        }else{
            return false;
        }
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity a = getActivity();
        pos = position;
        a.openContextMenu(v);
    }
}
