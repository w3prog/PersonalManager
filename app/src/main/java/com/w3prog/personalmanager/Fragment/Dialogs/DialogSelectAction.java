package com.w3prog.personalmanager.Fragment.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class DialogSelectAction extends DialogFragment {
    public static final String EXTRA_ACTION = "DatePickerFragment.EXTRA_ACTION";

    private long ActionID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Action> actions = new ArrayList<Action>(
                DataBase.get(getActivity()).getActions());
        if (actions.size() == 0) {
            //todo решить проблему нулевых выборов.
        }
        final int actionSize = actions.size();
        CharSequence[] s = new CharSequence[actionSize];
        for (int i = 0; i < actionSize; i++) {
            s[i] = actions.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.SelectTask)
                .setItems(s, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActionID = actions.get(which).getId();
                        sendResult(Activity.RESULT_OK);
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultOk) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_ACTION, ActionID);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultOk, i);
    }
}
