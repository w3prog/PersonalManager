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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Contact;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentEditPerson extends Fragment {

    private Person person;
    private TextView textViewPost;
    private TextView textViewFirstName;
    private TextView textViewLastName;
    private ListView listViewPhoneNumber;
    private ListView listViewEmail;
    private ListView listViewWebSite;

    private Button buttonAddPhoneNumber;
    private Button buttonAddEmail;
    private Button buttonAddWebsite;

    private static final String TAG = "FragmentEditPerson";

    public static final String EXTRA_PERSON_ID = "com.w3prog.personalmanager." +
            "Fragment.FragmentEditPerson.EXTRA_PERSON_ID";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        long personID = getArguments().getLong(EXTRA_PERSON_ID);
        person = DataBase.Get(getActivity()).getPerson(personID);
        Log.d(TAG," FragmentEditPerson  onCreate");

        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_person,container,false);
        Log.d(TAG," FragmentEditPerson  onCreateView");
        textViewFirstName = (TextView)v.findViewById(R.id.person_edit_firstname);
        textViewFirstName.setText(person.getFirstName());
        textViewFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setFirstName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.d(TAG,person.getFirstName());
        textViewLastName = (TextView)v.findViewById(R.id.person_edit_lastname);
        textViewLastName.setText(person.getLastName());
        textViewLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.d(TAG,person.getLastName());
        textViewPost = (TextView)v.findViewById(R.id.person_edit_post);
        textViewPost.setText(person.getPost());
        textViewPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setPost(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // todo обновление списков.
        ArrayList<Contact> ConcactCollection = DataBase.
                Get(getActivity())
                .getPhoneContactPerson(person);
        ArrayList<Contact> workConcactCollection = new ArrayList<Contact>();

        for (Contact contact : ConcactCollection) {
            Log.e( TAG, contact.getType());
            if (contact.getType() == "Phone"){
                workConcactCollection.add(contact);
            } else {
                workConcactCollection.add(contact);
            }

        }

        for (Contact contact : workConcactCollection) {
            Log.e( TAG, " С внутреннего цикла " + contact.getType());
        }


        listViewPhoneNumber = (ListView)v.findViewById(R.id.NumberPhoneListView);
        ContactApapter contactsAd = new ContactApapter(ConcactCollection);

        listViewPhoneNumber.setAdapter(contactsAd);

        workConcactCollection = new ArrayList<Contact>();
        for (Contact contact : ConcactCollection) {
            Log.e( TAG, contact.getType());
            if (contact.getType() == "Email"){
                workConcactCollection.add(contact);
            } else {
                workConcactCollection.add(contact);
            }

        }


        listViewEmail = (ListView)v.findViewById(R.id.EmailListView);
        ContactApapter contactsEmail = new ContactApapter(workConcactCollection);
        listViewEmail.setAdapter(contactsEmail);

        for (Contact contact : ConcactCollection) {
            Log.e( TAG, contact.getType());
            if (contact.getType() == "Website"){
                workConcactCollection.add(contact);
            } else {
                workConcactCollection.add(contact);
            }

        }

        listViewWebSite = (ListView)v.findViewById(R.id.WebSiteListView);
        ContactApapter contactsWebSite = new ContactApapter(workConcactCollection);
        listViewWebSite.setAdapter(contactsWebSite);

        //добавление обработчиков кнопок добавления
        buttonAddPhoneNumber = (Button)v.findViewById(R.id.NumberPhoneAddButton);
        buttonAddPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to добавляет новый контакт в список и базу данных
            }
        });

        Log.d(TAG," FragmentEditPerson end onCreateView");
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //todo в перспективе это может приветсти к багу на планшетах
                Log.e(TAG,"Активити закрыла сама себя");
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static FragmentEditPerson newInstance(long id){
        Bundle args = new Bundle();
        args.putLong(EXTRA_PERSON_ID, id);
        FragmentEditPerson fragment = new FragmentEditPerson();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        DataBase.Get(getActivity()).updatePerson((int)person.getId(),person);
    }

    class ContactApapter extends ArrayAdapter<Contact> {

        public ContactApapter(ArrayList<Contact> contacts) {
            super(getActivity(), 0,contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_contact,null);
            }
            final Contact contact = getItem(position);

            EditText editTextDescription = (EditText)convertView
                    .findViewById(R.id.DescriptionContact);
            editTextDescription.setText(contact.getDescription());
            editTextDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    contact.setDescription(s.toString());
                    DataBase.Get(getActivity())
                            .updateContact((int)contact.getIdContact(),contact);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            ImageButton buttonDelete = (ImageButton)convertView
                    .findViewById(R.id.DeleteContactButton);
            buttonDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo должен удалить но хз как это сделать.
                    remove(contact);
                    DataBase.Get(getActivity()).deleteContact((int)contact.getIdContact());
                }
            });

            return convertView;
        }
    }
}
