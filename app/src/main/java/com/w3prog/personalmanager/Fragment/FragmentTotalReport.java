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
import android.widget.EditText;
import android.widget.TextView;

import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectTask;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;
import com.w3prog.personalmanager.Task;

import java.util.ArrayList;

public class FragmentTotalReport extends ListFragment {
    private static final int REQUEST_TASK = 1;
    private static final String DIALOG_TASK = "task";
    private Button buttonSelectTask;
    private Task task;
    private ActionAdapter adapter;
    private ArrayList<Person> persons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.header_total_report, null);
        buttonSelectTask = (Button) view
                .findViewById(R.id.SelectReportTask);
        buttonSelectTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectTask dialog = new DialogSelectTask();
                dialog.setTargetFragment(FragmentTotalReport.this, REQUEST_TASK);
                dialog.show(fm, DIALOG_TASK);
            }
        });

        getListView().addHeaderView(view);
        setListAdapter(null);
    }

    private void updateListView() {
        if (task != null) {
            persons = DataBase.get(getActivity())
                    .getReportTotalTask( task);
            if (persons != null) {
                adapter = new ActionAdapter(persons);
                setListAdapter(adapter);
            } else {
                setListAdapter(null);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_TASK) {
            long l = data.getLongExtra(DialogSelectTask.EXTRA_TASK, 0);
            task = DataBase.get(getActivity()).getTask(l);
            buttonSelectTask.setText(task.toString());
            updateListView();
        }
    }

    private class ActionAdapter extends ArrayAdapter<Person> {

        public ActionAdapter(ArrayList<Person> p) {
            super(getActivity(), 0, p);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_personals_result, null);
            }
            Person per = getItem(position);
            TextView textViewName = (TextView) convertView.findViewById(R.id.PersonFullName);
            textViewName.setText(per.toString());

            Long result = DataBase.get(getActivity()).getTotalResult(per.getId(),task.getId());

            EditText textViewResult = (EditText)convertView
                    .findViewById(R.id.editTextPersonsResult);
            textViewResult.setText(Long.toString(result));
            textViewResult.setEnabled(false);
            return convertView;
        }
    }
}
