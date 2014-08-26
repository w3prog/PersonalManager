package com.w3prog.personalmanager.Fragment.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class DialogSelectPerson extends DialogFragment {
    public static final String EXTRA_PERSON = "DatePickerFragment.EXTRA_PERSON";

    private long PersonID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ArrayList<Person> persons = new ArrayList<Person>(
                DataBase.get(getActivity()).getPersons());
        if (persons.size() == 0) {
            //todo решить проблему нулевых выборов.
        }

        final int personSize = persons.size();
        CharSequence[] s = new CharSequence[personSize];
        for (int i = 0; i < personSize; i++) {
            s[i] = persons.get(i).getFirstName() + " " +
                    persons.get(i).getLastName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.SelectTask)
                .setItems(s, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PersonID = persons.get(which).getId();
                        sendResult(Activity.RESULT_OK);
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultOk) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_PERSON, PersonID);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultOk, i);
    }
}
