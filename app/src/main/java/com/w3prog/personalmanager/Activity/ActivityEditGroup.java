package com.w3prog.personalmanager.Activity;

import android.app.Fragment;

import com.w3prog.personalmanager.Fragment.Edit.FragmentEditGroup;

public class ActivityEditGroup extends SimpleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        long L = getIntent().getLongExtra(FragmentEditGroup.EXTRA_GROUP_ID, 0);
        return FragmentEditGroup.newInstance(L);
    }
}
