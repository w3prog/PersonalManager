package com.w3prog.personalmanager.Activity;

import android.app.Fragment;

import com.w3prog.personalmanager.Fragment.Edit.FragmentEditPerson;

public class ActivityEditPerson extends SimpleFragmentActivity {
    @Override
    protected Fragment getFragment() {

        long L = getIntent().getLongExtra(FragmentEditPerson.EXTRA_PERSON_ID, 0);
        return FragmentEditPerson.newInstance(L);
    }
}
