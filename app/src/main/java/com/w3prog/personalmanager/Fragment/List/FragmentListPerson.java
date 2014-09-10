package com.w3prog.personalmanager.Fragment.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Activity.ActivityEditPerson;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Edit.FragmentEditPerson;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentListPerson extends ListFragment {
    private static final String TAG = "FragmentListPerson";
    private ArrayList<Person> personArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personArrayList = DataBase.get(getActivity()).getPersons();
        PersonAdapter personAdapter = new PersonAdapter(personArrayList);
        setListAdapter(personAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.footer_list_person, null);
        Button buttonFooter = (Button) view.findViewById(R.id.FooterListPerson);
        buttonFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l = DataBase.get(getActivity()).insertNewPerson();
                Intent i = new Intent(getActivity(), ActivityEditPerson.class);
                i.putExtra(FragmentEditPerson.EXTRA_PERSON_ID, l);
                startActivity(i);
            }
        });
        getListView().addFooterView(view);

        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position,
                                                  long id,
                                                  boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflaterMenu = mode.getMenuInflater();
                inflaterMenu.inflate(R.menu.context_menu_list, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deleteItem:
                        PersonAdapter adapter = (PersonAdapter) getListAdapter();
                        personArrayList = DataBase.get(getActivity()).getPersons();
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                DataBase.get(getActivity()).deletePerson(
                                        adapter.getItem(i).getId());
                            }
                        }
                        mode.finish();
                        personArrayList = DataBase.get(getActivity()).getPersons();
                        adapter = new PersonAdapter(personArrayList);
                        setListAdapter(adapter);
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Person c = ((PersonAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), ActivityEditPerson.class);
        i.putExtra(FragmentEditPerson.EXTRA_PERSON_ID, c.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        personArrayList = DataBase.get(getActivity()).getPersons();
        PersonAdapter personAdapter = new PersonAdapter(personArrayList);
        setListAdapter(personAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_person, menu);
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
