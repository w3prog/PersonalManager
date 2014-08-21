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
import com.w3prog.personalmanager.Fragment.Dialogs.DatePickerFragment;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectPerson;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.PersonUtil;
import com.w3prog.personalmanager.R;

import java.sql.Date;
import java.util.ArrayList;

public class FragmentReportOfDate extends ListFragment {
    private static final int REQUEST_LAST_DATE = 1;
    private static final int REQUEST_NEW_DATE = 10;
    private static final int REQUEST_PERSON = 11;
    private static final String DIALOG_LAST_DATE = "last_date";
    private static final String DIALOG_NEW_DATE = "new_date";
    private static final String DIALOG_PERSON = "person";
    private Button buttonSelectLastDate;
    private Button buttonSelectNewDate;
    private Button buttonSelectPerson;
    private Date dateLast = null;
    private Date dateNew = null;
    private Person person = null;
    private ActionAdapter adapter = null;
    private ArrayList<Action> actions = null;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.header_report, null);

        buttonSelectLastDate = (Button) view
                .findViewById(R.id.ReportFinishDate);
        buttonSelectLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(FragmentReportOfDate.this, REQUEST_LAST_DATE);
                dialog.show(fm, DIALOG_LAST_DATE);
            }
        });

        buttonSelectNewDate = (Button) view
                .findViewById(R.id.ReportStartDate);
        buttonSelectNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(FragmentReportOfDate.this, REQUEST_NEW_DATE);
                dialog.show(fm, DIALOG_NEW_DATE);
            }
        });

        buttonSelectPerson = (Button) view
                .findViewById(R.id.SelectReportDatePerson);
        buttonSelectPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectPerson dialog =
                        new DialogSelectPerson();
                dialog.setTargetFragment(FragmentReportOfDate.this, REQUEST_PERSON);
                dialog.show(fm, DIALOG_PERSON);
            }
        });

        getListView().addHeaderView(view);
        setListAdapter(null);
    }

    private void updateListView() {
        if (person != null && dateLast != null && dateNew !=null) {
            actions = DataBase.Get(getActivity())
                    .getReportPersonActionInDate(person, dateLast, dateNew);
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

        if (requestCode == REQUEST_LAST_DATE) {
            java.util.Date date = (java.util.Date)
                    data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            dateLast = new Date(date.getTime());
            buttonSelectNewDate.setText(PersonUtil.writeDate(dateLast));
        }

        if (requestCode == REQUEST_NEW_DATE){
            java.util.Date date = (java.util.Date)
                    data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            dateNew = new Date(date.getTime());
            buttonSelectNewDate.setText(PersonUtil.writeDate(dateNew));
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
