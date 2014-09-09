package com.w3prog.personalmanager.Fragment.Edit;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectPerson;
import com.w3prog.personalmanager.Group;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentEditGroup extends ListFragment {
    public static final String EXTRA_GROUP_ID = "FragmentEditGroup.EXTRA_GROUP_ID";
    private Group group;
    private static final String TAG = "FragmentEditGroup";
    private static ArrayList<Person> persons;
    private View viewFooter;
    private View viewHeader;
    private Button buttonAdd;
    private static final int REQUEST_PERSON = 1;
    private static final String DIALOG_PERSON = "person";
    private PersonAdapter adapter;

    //todo добавить возможность обновления 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long groupID = getArguments().getLong(EXTRA_GROUP_ID);
        Log.e(TAG, Long.toString(groupID));
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        group = DataBase.get(getActivity()).getGroup(groupID);
        persons = DataBase.get(getActivity()).getRowsPersonInGroup(group);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        viewHeader = getActivity().getLayoutInflater()
                .inflate(R.layout.edit_group, null);

        EditText editTextName = (EditText) viewHeader.findViewById(R.id.GroupEditTitle);
        editTextName.setText(group.getName());
        editTextName.addTextChangedListener(new TextWatcher() {
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

        EditText editTextDescription = (EditText) viewHeader
                .findViewById(R.id.GroupEditDescription);
        editTextDescription.setText(group.getDescription());
        editTextDescription.addTextChangedListener(new TextWatcher() {
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

        viewFooter = getActivity().getLayoutInflater()
                .inflate(R.layout.footer_list_person, null);

        buttonAdd = (Button)viewFooter.findViewById(R.id.FooterListPerson);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectPerson dialog = new DialogSelectPerson();
                dialog.setTargetFragment(FragmentEditGroup.this, REQUEST_PERSON);
                dialog.show(fm, DIALOG_PERSON);
            }
        });

        getListView().addHeaderView(viewHeader);
        getListView().addFooterView(viewFooter);
        if (persons!=null&&!(persons.size()>0)){
            adapter = new PersonAdapter(persons);
            setListAdapter(adapter);
        }else {
            setListAdapter(null);
        }

        super.onActivityCreated(savedInstanceState);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_PERSON){
            long l = data.getLongExtra(DialogSelectPerson.EXTRA_PERSON, 0);
            Person personNew = DataBase.get(getActivity()).getPerson(l);
            for (Person item :persons) {
                if (item.getId() == personNew.getId()){
                    Toast.makeText(getActivity(),
                            "Данный человек уже присутствует в группе", Toast.LENGTH_SHORT);
                    return;
                }

            }

            DataBase.get(getActivity()).insertRowPersonInGroup(personNew.getId(), group.getId());
            if (persons == null)
                persons = new ArrayList<Person>();
            persons.add(personNew);
            if (adapter == null)
                adapter = new PersonAdapter(persons);
            updateListPerson();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateListPerson() {
       setListAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        DataBase.get(getActivity()).updateGroup(group.getId(), group);
    }

    public static Fragment newInstance(long l) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_GROUP_ID, l);
        FragmentEditGroup fragment = new FragmentEditGroup();
        fragment.setArguments(args);
        return fragment;
    }

    public class PersonAdapter extends ArrayAdapter<Person> {

        public PersonAdapter(ArrayList<Person> Persons) {
            super(getActivity(), 0, Persons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_person, null);
            }
            Person c = getItem(position);

            TextView textViewName = (TextView) convertView.findViewById(R.id.item_FullName);
            textViewName.setText(c.getFirstName() + " " + c.getLastName());

            TextView textViewPost = (TextView) convertView.findViewById(R.id.item_post);
            textViewPost.setText(c.getPost());

            return convertView;
        }
    }
}
