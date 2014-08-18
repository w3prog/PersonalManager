package com.w3prog.personalmanager.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.R;
import com.w3prog.personalmanager.Task;

public class FragmentEditTask extends Fragment {
    public static final String EXTRA_ACTION_ID = "FragmentEditTask.EXTRA_ACTION_ID";
    private TextView textViewName;
    private TextView textViewDescriproin;
    private Task task;
    private static final String TAG = "FragmentEditAction";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long taskID = getArguments().getLong(EXTRA_ACTION_ID);
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        task = DataBase.Get(getActivity()).getTask(taskID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_action,container,false);
        textViewName = (TextView)v.findViewById(R.id.TaskName);

        textViewName.setText(task.getName());
        textViewName.addTextChangedListener( new TextWatcher() {
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


        textViewDescriproin = (TextView)v.findViewById(R.id.TaskDescription);
        textViewDescriproin.setText(task.getDescription());
        textViewDescriproin.addTextChangedListener(new TextWatcher() {
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


    public static FragmentEditAction newInstance(long id){
        Bundle args = new Bundle();
        args.putLong(EXTRA_ACTION_ID, id);
        FragmentEditAction fragment = new FragmentEditAction();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onPause() {
        super.onPause();

        //todo странно что здесь int стоит нужно разобраться можно ли поставить long
        DataBase.Get(getActivity()).updateTask((int) task.getId(), task);
    }
}


