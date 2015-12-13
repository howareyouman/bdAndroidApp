package com.example.thinkpad.testretrofitapp.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.activities.MainActivity;
import com.example.thinkpad.testretrofitapp.basicclasses.Song;

import java.util.ArrayList;

/**
 * Created by Арсений on 12/13/2015.
 */
public class ViewSongsFromEverywhereFragment extends ListFragment {
    private ArrayList<String> arrayStrings;
    private ArrayList<Song> arraySongs;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        MainActivity activity = (MainActivity) getActivity();

        switch (activity.what) {
            case 0:
                break;
            case 1:
                arraySongs = activity.getSongsByGroup();
                break;
            case 2:
                arraySongs = activity.getSongsByPlaylist();
                break;
        }
        if(activity.what  > 0) {
            arrayStrings = new ArrayList<>();

            for (int i = 0; i < arraySongs.size(); i++)
                arrayStrings.add(arraySongs.get(i).toString());


            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    R.layout.custom_textview, arrayStrings);

            setListAdapter(adapter);
        }

        return view;

    }

    private void upgrade(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }
}
