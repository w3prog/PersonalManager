package com.w3prog.personalmanager.Fragment.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.R;
import com.w3prog.personalmanager.Task;

import java.util.ArrayList;

public class DialogSelectTask extends DialogFragment {
    public static final String EXTRA_TASK = "DatePickerFragment.EXTRA_TASK";

    private long TaskID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ArrayList<Task> tasks = new ArrayList<Task>(
                DataBase.Get(getActivity()).getTasks());
        if (tasks.size() == 0) {
            //todo решить проблему нулевых задач
        }

        final int tasksize = tasks.size();
        CharSequence[] s = new CharSequence[tasksize];
        for (int i = 0; i < tasksize; i++) {
            s[i] = tasks.get(i).getName();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.SelectTask)
                .setItems(s, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        TaskID = tasks.get(which).getId();
                        sendResult(Activity.RESULT_OK);
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultOk) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_TASK, TaskID);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultOk, i);
    }
}
