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
    private MainActivity activity;
    private int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        activity = (MainActivity) getActivity();

        arrayGroups = activity.getAllGroups();
        arrayStrings = new ArrayList<>();

        for(int i=0;i<arrayGroups.size();i++)
            arrayStrings.add(arrayGroups.get(i).toString());


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
        menu.add(0, v.getId(), 0, "Show songs");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete group");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Show songs"){
            activity.what = 1;
            activity.nameOfWhat = arrayGroups.get(pos).groupName;
            ViewSongsFromEverywhereFragment v = new ViewSongsFromEverywhereFragment();
            activity.setNewFragment(v);
        }  else if(item.getTitle()=="Delete group"){
            if(activity.deleteGroup(arrayGroups.get(pos).groupName))
                upgrade();
        } else {
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
