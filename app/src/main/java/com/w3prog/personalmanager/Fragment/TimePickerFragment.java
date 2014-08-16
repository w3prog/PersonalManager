package com.w3prog.personalmanager.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.w3prog.personalmanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "TimePickerFragment.EXTRA_DATE";
    private Date date;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }


    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        date = (Date)getArguments().getSerializable(EXTRA_TIME);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour =calendar.get(Calendar.HOUR);
        int minute =calendar.get(Calendar.MINUTE);
        final int second =calendar.get(Calendar.SECOND);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time, null);
        //todo это метод лучше попробовать заменить другим или даже стандартным


        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentMinute(minute);
        timePicker.setCurrentHour(hour);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                date = new GregorianCalendar(year, month, day, hourOfDay, minute, second).getTime();
                getArguments().putSerializable(EXTRA_TIME, date);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(getString(R.string.SelectTime))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
