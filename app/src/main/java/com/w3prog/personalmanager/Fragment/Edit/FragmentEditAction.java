package com.w3prog.personalmanager.Fragment.Edit;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Dialogs.DatePickerFragment;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectTask;
import com.w3prog.personalmanager.Fragment.Dialogs.TimePickerFragment;
import com.w3prog.personalmanager.PersonUtil;
import com.w3prog.personalmanager.R;

import java.util.Date;

public class FragmentEditAction extends Fragment {

    private Button buttonDate;
    private Button buttonTime;
    private Button buttonTask;
    private TextView textViewName;
    private TextView textViewDescription;
    private Action action;
    private static final String TAG = "FragmentEditAction";

    public static final String EXTRA_ACTION_ID = "FragmentEditAction.EXTRA_ACTION_ID";
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 10;
    public static final int REQUEST_TASK = 30;

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final String DIALOG_TASK = "task";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long actionID = getArguments().getLong(EXTRA_ACTION_ID);
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        action = DataBase.get(getActivity()).getAction(actionID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_action, container, false);
        Log.d(TAG, " FragmentEditPerson  onCreateView");
        textViewName = (TextView) v.findViewById(R.id.ActionName);

        textViewName.setText(action.getName());
        textViewName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                action.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonDate = (Button) v.findViewById(R.id.ButtonDate);
        buttonDate.setText(PersonUtil.writeDate(action.getDate()));
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(action.getDate());
                dialog.setTargetFragment(FragmentEditAction.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        buttonTime = (Button) v.findViewById(R.id.ButtonTime);
        buttonTime.setText(PersonUtil.writeTime(action.getDate()));
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(action.getDate());
                dialog.setTargetFragment(FragmentEditAction.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });


        buttonTask = (Button) v.findViewById(R.id.ActionTask);
        if (action.getTask() == null)
            buttonTask.setText(getString(R.string.SelectTask));
        else
            buttonTask.setText(action.getTask().getName());
        buttonTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectTask dialog = new DialogSelectTask();
                dialog.setTargetFragment(FragmentEditAction.this, REQUEST_TASK);
                dialog.show(fm, DIALOG_TASK);
            }
        });

        textViewDescription = (TextView) v.findViewById(R.id.ActionDescription);
        textViewDescription.setText(action.getDescription());
        textViewDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                action.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e(TAG, "Активити закрыла сама себя");
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static FragmentEditAction newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_ACTION_ID, id);
        FragmentEditAction fragment = new FragmentEditAction();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        DataBase.get(getActivity()).updateAction((int) action.getId(), action);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            action.setDate(date);
            buttonDate.setText(PersonUtil.writeDate(action.getDate()));
        }
        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            action.setDate(date);
            buttonTime.setText(PersonUtil.writeTime(action.getDate()));
        }
        if (requestCode == REQUEST_TASK) {
            action.setTask(DataBase.
                    get(getActivity())
                    .getTask(data
                            .getIntExtra(DialogSelectTask.EXTRA_TASK, 1)));
            buttonTask.setText(action.getTask().getName());
            DataBase.get(getActivity()).updateAction((int) action.getId(), action);
        }
    }

}
