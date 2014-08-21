package com.w3prog.personalmanager.Fragment.Edit;

import android.app.Fragment;
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

import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Group;
import com.w3prog.personalmanager.R;

public class FragmentEditGroup extends Fragment {
    public static final String EXTRA_GROUP_ID = "FragmentEditGroup.EXTRA_GROUP_ID";
    private TextView textViewName;
    private TextView textViewDescriproin;
    private Group group;
    private static final String TAG = "FragmentEditGroup";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long groupID = getArguments().getLong(EXTRA_GROUP_ID);
        Log.e(TAG, Long.toString(groupID));
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        group = DataBase.Get(getActivity()).getGroup(groupID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_group,container,false);

        textViewName = (Button)view.findViewById(R.id.GroupEditTitle);
        textViewName.setText(group.getName());
        textViewName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                group.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textViewDescriproin = (Button)view.findViewById(R.id.GroupEditDescription);
        textViewDescriproin.setText(group.getDescription());
        textViewDescriproin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                group.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        DataBase.Get(getActivity()).updateGroup(group.getId(), group);
    }

    public static Fragment newInstance(long l) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_GROUP_ID, l);
        FragmentEditGroup fragment = new FragmentEditGroup();
        fragment.setArguments(args);
        return fragment;
    }
}
