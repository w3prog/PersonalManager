package com.w3prog.personalmanager.Fragment.Edit;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.w3prog.personalmanager.Contact;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentEditPerson extends ListFragment {

    private Person person;
    private TextView textViewPost;
    private TextView textViewFirstName;
    private TextView textViewLastName;
    private Button buttonAddPhoneNumber;
    private ImageView imageViewLogo;
    static final int GALLERY_REQUEST = 1;
    View viewHeader;
    View viewFooter;

    ContactAdapter contactsAd;
    private static final String TAG = "FragmentEditPerson";

    public static final String EXTRA_PERSON_ID = "com.w3Prog.personalManager." +
            "Fragment.FragmentEditPerson.EXTRA_PERSON_ID";

    ArrayList<Contact> ContactCollection;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        ContactCollection = DataBase.
                get(getActivity())
                .getPhoneContactPerson(person);

        contactsAd = new ContactAdapter(ContactCollection);
        setListAdapter(contactsAd);
    }

    @Override
    public void onAttach(Activity activity) {
        long personID = getArguments().getLong(EXTRA_PERSON_ID);
        person = DataBase.get(getActivity()).getPerson(personID);

        viewHeader = getActivity().getLayoutInflater().
                inflate(R.layout.header_edit_person, null);

        textViewFirstName = (TextView) viewHeader.findViewById(R.id.person_edit_firstname);
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

        textViewLastName = (TextView) viewHeader.findViewById(R.id.person_edit_lastname);
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

        textViewPost = (TextView) viewHeader.findViewById(R.id.person_edit_post);
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

        imageViewLogo = (ImageView) viewHeader.findViewById(R.id.logoPerson);
        if (person.getStrImg() != "" || person.getStrImg() != null){
            Log.e(TAG,"Открытие картинки");
            Log.e(TAG,person.getStrImg());
            imageViewLogo.setImageURI(Uri.parse(person.getStrImg()));
        }

        imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        viewFooter = getActivity().getLayoutInflater()
                .inflate(R.layout.footer_edit_person,null);

        buttonAddPhoneNumber = (Button) viewFooter.findViewById(R.id.NumberPhoneAddButton);
        buttonAddPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase.get(getActivity()).insertNewConcact(person);
                updateListView();
            }
        });

        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getListView().addHeaderView(viewHeader);
        getListView().addFooterView(viewFooter);
        super.onActivityCreated(savedInstanceState);
    }

    private void updateListView(){
        contactsAd.clear();
        contactsAd.addAll(DataBase.
                get(getActivity())
                .getPhoneContactPerson(person));
        getListView().setAdapter(contactsAd);
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

    public static FragmentEditPerson newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_PERSON_ID, id);
        FragmentEditPerson fragment = new FragmentEditPerson();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        DataBase.get(getActivity()).updatePerson((int) person.getId(), person);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = data.getData();
                    imageViewLogo.setImageURI(selectedImage);

                    Log.e(TAG,data.getData().toString());
                    person.setStrImg(data.getData().toString());
                }
        }
    }

    class ContactAdapter extends ArrayAdapter<Contact> {

        public ContactAdapter(ArrayList<Contact> contacts) {
            super(getActivity(), 0, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_contact, null);
            }
            final Contact contact = getItem(position);

            EditText editTextDescription = (EditText) convertView
                    .findViewById(R.id.DescriptionContact);
            editTextDescription.setText(contact.getDescription());
            editTextDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    contact.setDescription(s.toString());
                    DataBase.get(getActivity())
                            .updateContact((int) contact.getIdContact(), contact);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            ImageButton buttonCall = (ImageButton) convertView
                    .findViewById(R.id.imgCall);
            buttonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contact.getDescription()));
                    startActivity(intent);
                }
            });
            ImageButton buttonDelete = (ImageButton) convertView
                    .findViewById(R.id.DeleteContactButton);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(contact);
                    DataBase.get(getActivity()).deleteContact((int) contact.getIdContact());
                }
            });

            return convertView;
        }
    }
}
