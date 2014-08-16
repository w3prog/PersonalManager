package com.w3prog.personalmanager.Activity;

import android.app.Fragment;

import com.w3prog.personalmanager.Fragment.FragmentListPerson;


public class ActivityListPerson extends SimpleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return new FragmentListPerson();
    }
}
