package com.w3prog.personalmanager.Fragment.Edit;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.R;
import com.w3prog.personalmanager.Task;

import java.util.ArrayList;

public class FragmentEditTask extends Fragment {
    public static final String EXTRA_TASK_ID = "FragmentEditTask.EXTRA_TASK_ID";
    private Task task;
    private static final String TAG = "FragmentEditTask";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long taskID = getArguments().getLong(EXTRA_TASK_ID);
        Log.e(TAG, Long.toString(taskID));
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        task = DataBase.get(getActivity()).getTask(taskID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_task, container, false);
        TextView textViewName = (TextView) v.findViewById(R.id.TaskName);

        if (task.getName() != "" || task.getName() != null)
            textViewName.setText(task.getName());

        textViewName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        TextView textViewDescription = (TextView) v.findViewById(R.id.TaskDescription);
        textViewDescription.setText(task.getDescription());
        textViewDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView listViewAction = (ListView) v.findViewById(R.id.listViewAction);

        ActionAdapter adapter = new ActionAdapter(
                DataBase.get(getActivity()).getActionOfTask(task)
        );
        listViewAction.setAdapter(adapter);

        return v;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static FragmentEditTask newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_TASK_ID, id);
        FragmentEditTask fragment = new FragmentEditTask();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onPause() {
        super.onPause();
        DataBase.get(getActivity()).updateTask((int) task.getId(), task);
    }

    private class ActionAdapter extends ArrayAdapter<Action> {

        public ActionAdapter(ArrayList<Action> actions) {
            super(getActivity(), 0, actions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }
            Action action = getItem(position);

            TextView textViewName = (TextView) convertView.findViewById(android.R.id.text1);
            textViewName.setText(action.getName());
            return convertView;
        }

    }
}


