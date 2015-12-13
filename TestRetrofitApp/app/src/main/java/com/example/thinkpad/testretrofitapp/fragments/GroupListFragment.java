package com.example.thinkpad.testretrofitapp.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thinkpad.testretrofitapp.R;
import com.example.thinkpad.testretrofitapp.activities.MainActivity;
import com.example.thinkpad.testretrofitapp.basicclasses.Group;

import java.util.ArrayList;

/**
 * Created by Арсений on 12/11/2015.
 */
public class GroupListFragment extends ListFragment {
    private ArrayList<String> arrayStrings;
    private ArrayList<Group> arrayGroups;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        MainActivity activity = (MainActivity) getActivity();

        arrayGroups = activity.getAllGroups();
        arrayStrings = new ArrayList<>();

        for(int i=0;i<arrayGroups.size();i++)
            arrayStrings.add(arrayGroups.get(i).toString());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.custom_textview, arrayStrings);

        setListAdapter(adapter);
        return view;

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }
}
