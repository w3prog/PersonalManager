package com.w3prog.personalmanager.Fragment.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectAction;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectPerson;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.PersonUtil;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentListPersonInAction extends ListFragment {

    private static final String TAG = "FragmentListPersonInAction";
    private static final int REQUEST_PERSON = 1;
    private static final int REQUEST_ACTION = 10;
    private static final String DIALOG_ACTION = "action";
    private static final String DIALOG_PERSON = "person";
    private PersonsResultAdapter personAdapter;
    private ArrayList<Person> personArrayList;
    private Action action = null;
    private Button buttonHeader;
    private Button buttonFooter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setData();
        super.onActivityCreated(savedInstanceState);
    }

    private void setData() {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater
                .inflate(R.layout.header_person_in_action, null);

        buttonHeader = (Button) view.findViewById(R.id.addPersonInAction);
        buttonHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectAction dialog = new DialogSelectAction();
                dialog.setTargetFragment(FragmentListPersonInAction.this, REQUEST_ACTION);
                dialog.show(fm, DIALOG_ACTION);
            }
        });
        ListView listView = getListView();
        listView.addHeaderView(view);

        View view1 = inflater
                .inflate(R.layout.footer_person_in_action, null);

        buttonFooter = (Button) view1.findViewById(R.id.FooterPersonInAction);
        buttonFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getFragmentManager();
                DialogSelectPerson dialog = new DialogSelectPerson();
                dialog.setTargetFragment(FragmentListPersonInAction.this, REQUEST_PERSON);
                dialog.show(fm, DIALOG_PERSON);
            }
        });

        listView.addFooterView(view1);
        setHasOptionsMenu(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflaterMenu = mode.getMenuInflater();
                inflaterMenu.inflate(R.menu.context_menu_fragment_person_in_action, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.DeletePersonIA:
                        //todo нужно проверить корректность удаления.
                        for (int i = personAdapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                DataBase.get(getActivity())
                                        .deleteRowPersonInAction(
                                                personAdapter.getItem(i+1).getPerson().getId(),
                                                action.getId());
                            }
                        }
                        mode.finish();
                        action = DataBase.get(getActivity()).getRowsPersonInAction(action);
                        personAdapter = new PersonsResultAdapter(action.getPersonsInAction());
                        setListAdapter(personAdapter);
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        setListAdapter(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_ACTION) {
            long l = data.getLongExtra(DialogSelectAction.EXTRA_ACTION, 0);
            action = DataBase.get(getActivity()).getAction(l);
            buttonHeader.setText(action.getName());
            updateListPerson();
        }
        if (requestCode == REQUEST_PERSON) {
            long l = data.getLongExtra(DialogSelectPerson.EXTRA_PERSON, 0);
            Person person = DataBase.get(getActivity()).getPerson(l);

            for (Person item :personArrayList) {
                if (item.getId() == person.getId()){
                    Toast.makeText(getActivity(),
                            "Данный человек уже присутствует в мероприятии",Toast.LENGTH_SHORT);
                    return;
                }

            }

            DataBase.get(getActivity()).insertRowPersonInAction(person.getId(), action.getId());
            if (personArrayList == null)
                personArrayList = new ArrayList<Person>();
            personArrayList.add(person);
            if (personAdapter == null)
                personAdapter = new PersonsResultAdapter(action.getPersonsInAction());
            updateListPerson();
        }
    }

    private void updateListPerson() {
        action.getPersonsInAction().clear();
        action = DataBase.get(getActivity()).getRowsPersonInAction(action);
        if (action.getPersonsInAction() != null) {

            personAdapter = new PersonsResultAdapter(action.getPersonsInAction());
            for (Action.PersonsResult personsResult :action.getPersonsInAction()) {
                Log.e(TAG, personsResult.toString());
            }
            setListAdapter(personAdapter);
        } else {
            setListAdapter(null);
        }
    }

    public class PersonsResultAdapter extends ArrayAdapter<Action.PersonsResult> {

        public PersonsResultAdapter(ArrayList<Action.PersonsResult> Persons) {
            super(getActivity(), 0, Persons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_personals_result, null);
            }
            final Action.PersonsResult c = getItem(position);
            TextView textViewName = (TextView) convertView.findViewById(R.id.PersonFullName);
            textViewName.setText(c.getPerson().toString());
            EditText textViewResult = (EditText) convertView.findViewById(R.id.editTextPersonsResult);
            textViewResult.setText(c.getRes().toString());
            textViewResult.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    Long l = 0L;
                    if (PersonUtil.checkString(s.toString()))
                        Long.decode(s.toString());
                    Log.e(TAG,l.toString());
                    if (l != null)
                        DataBase.get(getActivity())
                                .updateRowPersonInAction(c.getPerson().getId(),
                                        action.getId(),
                                        l);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return convertView;
        }
    }


}
