package com.w3prog.personalmanager.Fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Activity.ActivityEditAction;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Task;

import java.util.ArrayList;

public class FragmentListTask extends ListFragment {
    private static final String TAG = "FragmentListTask";
    private ArrayList<Task> taskArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskArrayList = DataBase.Get(getActivity()).getTasks();
        setHasOptionsMenu(true);
        TaskAdapter taskAdapter = new TaskAdapter(taskArrayList);
        setListAdapter(taskAdapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Task task = ((TaskAdapter)getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(),ActivityEditAction.class);
        i.putExtra(FragmentEditTask.EXTRA_ACTION_ID,task.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {

        super.onResume();
        taskArrayList = DataBase.Get(getActivity()).getTasks();

        //todo Можно ли это сделать попроще

        TaskAdapter taskAdapter = new TaskAdapter(taskArrayList);
        setListAdapter(taskAdapter);
    }


    private class TaskAdapter extends ArrayAdapter<Task> {

        public TaskAdapter(ArrayList<Task> Tasks) {
            super(getActivity(), 0,Tasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1,null);
            }
            Task task = getItem(position);

            TextView textViewName = (TextView)convertView.findViewById(android.R.id.text1);
            textViewName.setText(task.getName());
            return convertView;
        }

    }
}
