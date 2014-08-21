package com.w3prog.personalmanager.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectPerson;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectTask;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;
import com.w3prog.personalmanager.Task;

import java.util.ArrayList;

public class FragmentReport extends ListFragment {
    private static final int REQUEST_TASK = 1;
    private static final int REQUEST_PERSON = 10;
    private static final String DIALOG_TASK = "task";
    private static final String DIALOG_PERSON = "person";
    private Button buttonSelectTask;
    private Button buttonSelectPerson;
    private Task task;
    private Person person;
    private ActionAdapter adapter;
    private ArrayList<Action> actions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.header_report, null);
        buttonSelectTask = (Button) view
                .findViewById(R.id.SelectReportTask);
        buttonSelectTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectTask dialog = new DialogSelectTask();
                dialog.setTargetFragment(FragmentReport.this, REQUEST_TASK);
                dialog.show(fm, DIALOG_TASK);
            }
        });
        buttonSelectPerson = (Button) view
                .findViewById(R.id.SelectReportPerson);
        buttonSelectPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectPerson dialog =
                        new DialogSelectPerson();
                dialog.setTargetFragment(FragmentReport.this, REQUEST_PERSON);
                dialog.show(fm, DIALOG_PERSON);
            }
        });
        getListView().addHeaderView(view);
        setListAdapter(null);
    }

    private void updateListView() {
        if (person != null && task != null) {
            actions = DataBase.Get(getActivity())
                    .getReportPersonActionIntask(person, task);
            if (actions != null) {
                adapter = new ActionAdapter(actions);
                setListAdapter(adapter);
            } else {
                setListAdapter(null);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_PERSON) {
            long l = data.getLongExtra(DialogSelectPerson.EXTRA_PERSON, 0);
            person = DataBase.Get(getActivity()).getPerson(l);
            buttonSelectPerson.setText(person.toString());
            updateListView();
        }
        if (requestCode == REQUEST_TASK) {
            long l = data.getLongExtra(DialogSelectTask.EXTRA_TASK, 0);
            task = DataBase.Get(getActivity()).getTask(l);
            buttonSelectTask.setText(task.toString());
            updateListView();
        }
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
