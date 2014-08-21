package com.w3prog.personalmanager.Fragment.List;

import android.app.Activity;
import android.app.FragmentManager;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectAction;
import com.w3prog.personalmanager.Fragment.Dialogs.DialogSelectPerson;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentListPersonInAction extends ListFragment {

    private static final String TAG = "FragmentListPersonInAction";
    private static final int REQUEST_PERSON = 1;
    private static final int REQUEST_ACTION = 10;
    private static final String DIALOG_ACTION = "action";
    private static final String DIALOG_PERSON = "person";
    private PersonAdapter personAdapter;
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
        View view = (LinearLayout) inflater
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

        View view1 = (LinearLayout) inflater
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
                        //todo не всегда правильно удаляет
                        for (int i = personAdapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                DataBase.Get(getActivity())
                                        .deleteRowPersonInAction(
                                                personAdapter.getItem(i).getId(),
                                                action.getId());
                            }
                        }

                        mode.finish();
                        personArrayList = DataBase
                                .Get(getActivity())
                                .getRowsPersonInAction(action);
                        // помне это слишком большое нагромаждение.
                        // todo узнать можно ли не создавать новый адаптер
                        personAdapter = new PersonAdapter(personArrayList);
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
            action = DataBase.Get(getActivity()).getAction(l);
            buttonHeader.setText(action.getName());
            updateListPerson();
        }
        if (requestCode == REQUEST_PERSON) {
            long l = data.getLongExtra(DialogSelectPerson.EXTRA_PERSON, 0);
            Person person = DataBase.Get(getActivity()).getPerson(l);
            //todo решить проблему наличия в активити, выбранного человека, второй раз
            DataBase.Get(getActivity()).insertRowPersonInAction(person.getId(), action.getId());
            if (personArrayList == null)
                personArrayList = new ArrayList<Person>();
            personArrayList.add(person);
            if (personAdapter == null)
                personAdapter = new PersonAdapter(personArrayList);
            updateListPerson();
        }
    }

    private void updateListPerson() {
        personArrayList = DataBase.Get(getActivity()).getRowsPersonInAction(action);
        if (personArrayList != null) {
            personAdapter = new PersonAdapter(personArrayList);
            setListAdapter(personAdapter);
        } else {
            setListAdapter(null);
        }
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
