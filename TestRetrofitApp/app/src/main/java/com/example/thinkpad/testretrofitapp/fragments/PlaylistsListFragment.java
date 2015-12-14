package com.example.thinkpad.testretrofitapp.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.activities.MainActivity;
import com.example.thinkpad.testretrofitapp.basicclasses.Playlist;

import java.util.ArrayList;

/**
 * Created by Арсений on 12/12/2015.
 */
public class PlaylistsListFragment extends ListFragment
        implements View.OnClickListener {
    MainActivity activity;
    EditText title;
    private ArrayList<String> arrayStrings;
    private ArrayList<Playlist> arrayPlaylists;
    private int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_playlists, container, false);
         activity = (MainActivity) getActivity();
        arrayPlaylists = activity.getAllPlaylists();
        arrayStrings = new ArrayList<>();
        Button but = (Button)view.findViewById(R.id.newPlaylistButton);
        title = (EditText)view.findViewById(R.id.editPlaylist);

        but.setOnClickListener(this);

        for(int i=0;i<arrayPlaylists.size();i++)
            arrayStrings.add(arrayPlaylists.get(i).toString());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
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
        menu.add(0, v.getId(), 0, "Show songs");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.newPlaylistButton:
                String text = title.getText().toString();
                if(!text.equals("")) {
                    if (activity.newPlaylist(text)) {
                        title.setText("");
                        upgrade();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Show songs"){
            activity.what = 2;
            activity.nameOfWhat = arrayPlaylists.get(pos).playListName;
            ViewSongsFromEverywhereFragment v = new ViewSongsFromEverywhereFragment();
            activity.setNewFragment(v);
        }
        else if(item.getTitle()=="Delete"){
            if(activity.deletePlaylist(arrayPlaylists.get(pos).playListName)) {
                upgrade();
            }

        }else{
            return false;
        }
        return true;
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
