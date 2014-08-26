package com.w3prog.personalmanager.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w3prog.personalmanager.Person;
import com.w3prog.personalmanager.R;

public class FragmentReadPerson extends Fragment {
    private Person person;
    private static final String TAG = "FragmentEditPerson";

    public static final String EXTRA_PERSON_ID = "com.w3Prog.personalManager." +
            "Fragment.FragmentEditPerson.EXTRA_PERSON_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.read_person, container, false);

        //Пока не определю что конкретно здесь должно отображатся это бессмысленно
        //v.findViewById();

        return v;
    }
}
