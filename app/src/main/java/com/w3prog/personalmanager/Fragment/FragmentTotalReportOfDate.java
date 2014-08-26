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
import com.w3prog.personalmanager.Fragment.Dialogs.DatePickerFragment;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.PersonUtil;
import com.w3prog.personalmanager.R;

import java.sql.Date;
import java.util.ArrayList;

public class FragmentTotalReportOfDate extends ListFragment {
    private static final int REQUEST_LAST_DATE = 1;
    private static final int REQUEST_NEW_DATE = 10;
    private static final String DIALOG_LAST_DATE = "last_date";
    private static final String DIALOG_NEW_DATE = "new_date";
    private Button buttonSelectLastDate;
    private Button buttonSelectNewDate;
    private Date dateLast = null;
    private Date dateNew = null;
    private ActionAdapter adapter = null;
    private ArrayList<Person> persons = null;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.header_total_report_date, null);

        buttonSelectLastDate = (Button) view
                .findViewById(R.id.ReportFinishDate);
        buttonSelectLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(new java.util.Date());
                dialog.setTargetFragment(FragmentTotalReportOfDate.this, REQUEST_LAST_DATE);
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
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(new java.util.Date());

                dialog.setTargetFragment(FragmentTotalReportOfDate.this, REQUEST_NEW_DATE);
                dialog.show(fm, DIALOG_NEW_DATE);
            }
        });


        getListView().addHeaderView(view);
        setListAdapter(null);
    }

    private void updateListView() {
        if (dateLast != null && dateNew != null) {
            persons = DataBase.get(getActivity())
                    .getTotalReportOfTime( dateLast, dateNew);
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

        if (requestCode == REQUEST_LAST_DATE) {
            java.util.Date date = (java.util.Date)
                    data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            dateLast = new Date(date.getTime());
            buttonSelectLastDate.setText(PersonUtil.writeDate(dateLast));
            updateListView();
        }

        if (requestCode == REQUEST_NEW_DATE) {
            java.util.Date date = (java.util.Date)
                    data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            dateNew = new Date(date.getTime());
            buttonSelectNewDate.setText(PersonUtil.writeDate(dateNew));
            updateListView();
        }
    }

    private class ActionAdapter extends ArrayAdapter<Person> {

        public ActionAdapter(ArrayList<Person> pers) {
            super(getActivity(), 0, pers);
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

            Long result = DataBase.get(getActivity())
                    .getTotalResultOfDate(dateLast,dateNew,per.getId());

            EditText textViewResult = (EditText)convertView
                    .findViewById(R.id.editTextPersonsResult);
            textViewResult.setText(Long.toString(result));
            textViewResult.setEnabled(false);
            return convertView;
        }
    }
}
