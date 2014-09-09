package com.w3prog.personalmanager.Fragment.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Activity.ActivityEditGroup;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Edit.FragmentEditGroup;
import com.w3prog.personalmanager.Group;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentListGroup extends ListFragment {
    private static final String TAG = "FragmentListGroup";
    private ArrayList<Group> groupArrayList;
    private View viewFooter;
    private Button buttonAdd;
    GroupAdapter groupAdapter = null;
    //todo реализовать удаление
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        viewFooter = getActivity().getLayoutInflater()
                .inflate( R.layout.footer_list_group,null);
        buttonAdd = (Button) viewFooter.findViewById(R.id.add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long l = DataBase.get(getActivity()).insertNewGroup();
                Intent i = new Intent(getActivity(), ActivityEditGroup.class);
                i.putExtra(FragmentEditGroup.EXTRA_GROUP_ID, l);
                startActivity(i);
            }
        });
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getListView().addFooterView(viewFooter);
        setData();
        super.onActivityCreated(savedInstanceState);
    }

    private void setData() {
        groupArrayList = DataBase.get(getActivity()).getGroups();
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
        groupArrayList = DataBase.get(getActivity()).getGroups();
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
