package com.w3prog.personalmanager.Fragment.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Activity.ActivityEditGroup;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Edit.FragmentEditGroup;
import com.w3prog.personalmanager.Group;

import java.util.ArrayList;

public class FragmentListGroup extends ListFragment {
    private static final String TAG = "FragmentListGroup";
    private ArrayList<Group> groupArrayList;
    GroupAdapter groupAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setData();
        super.onActivityCreated(savedInstanceState);
    }

    private void setData() {
        groupArrayList = DataBase.Get(getActivity()).getGroups();
        setHasOptionsMenu(true);
        groupAdapter = new GroupAdapter(groupArrayList);
        setListAdapter(groupAdapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Group group = groupAdapter.getItem(position);
        Intent i = new Intent(getActivity(), ActivityEditGroup.class);
        i.putExtra(FragmentEditGroup.EXTRA_GROUP_ID, group.getId());
        startActivity(i);
    }


    @Override
    public void onResume() {
        super.onResume();
        groupArrayList = DataBase.Get(getActivity()).getGroups();
        //todo Спросить можно ли это сделать попроще
        GroupAdapter groupAdapter = new GroupAdapter(groupArrayList);
        setListAdapter(groupAdapter);
    }


    private class GroupAdapter extends ArrayAdapter<Group> {

        public GroupAdapter(ArrayList<Group> groups) {
            super(getActivity(), 0, groups);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }
            Group group = getItem(position);

            TextView textViewName = (TextView) convertView.findViewById(android.R.id.text1);
            textViewName.setText(group.toString());
            return convertView;
        }

    }
}