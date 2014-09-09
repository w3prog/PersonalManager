package com.w3prog.personalmanager.Fragment.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Activity.ActivityEditTask;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Edit.FragmentEditTask;
import com.w3prog.personalmanager.R;
import com.w3prog.personalmanager.Task;

import java.util.ArrayList;

public class FragmentListTask extends ListFragment {
    private static final String TAG = "FragmentListTask";
    private ArrayList<Task> taskArrayList;

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
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.footer_list_task, null);

        Button buttonFooter = (Button) view.findViewById(R.id.FooterListTask);
        buttonFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l = DataBase.get(getActivity()).insertNewTask();
                Intent i = new Intent(getActivity(), ActivityEditTask.class);
                i.putExtra(FragmentEditTask.EXTRA_TASK_ID, l);
                startActivity(i);
            }
        });
        ListView listView = getListView();
        listView.addFooterView(view);

        taskArrayList = DataBase.get(getActivity()).getTasks();
        setHasOptionsMenu(true);
        TaskAdapter taskAdapter = new TaskAdapter(taskArrayList);
        setListAdapter(taskAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position,
                                                  long id,
                                                  boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflaterMenu = mode.getMenuInflater();
                inflaterMenu.inflate(R.menu.context_menu_fragment_task, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.DeleteTask:
                        TaskAdapter adapter = (TaskAdapter) getListAdapter();
                        taskArrayList = DataBase.get(getActivity()).getTasks();
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                DataBase.get(getActivity()).deleteTask(
                                        adapter.getItem(i).getId());
                            }
                        }
                        mode.finish();
                        taskArrayList = DataBase.get(getActivity()).getTasks();
                        TaskAdapter taskAdapter = new TaskAdapter(taskArrayList);
                        setListAdapter(taskAdapter);
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Task task = ((TaskAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), ActivityEditTask.class);
        i.putExtra(FragmentEditTask.EXTRA_TASK_ID, task.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        taskArrayList = DataBase.get(getActivity()).getTasks();
        TaskAdapter taskAdapter = new TaskAdapter(taskArrayList);
        setListAdapter(taskAdapter);
    }


    private class TaskAdapter extends ArrayAdapter<Task> {

        public TaskAdapter(ArrayList<Task> Tasks) {
            super(getActivity(), 0, Tasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }
            Task task = getItem(position);

            TextView textViewName = (TextView) convertView.findViewById(android.R.id.text1);
            textViewName.setText(task.getName());
            return convertView;
        }

    }
}
