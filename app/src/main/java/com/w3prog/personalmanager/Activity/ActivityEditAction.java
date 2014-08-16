package com.w3prog.personalmanager.Activity;

import android.app.Fragment;

import com.w3prog.personalmanager.Fragment.FragmentEditAction;

public class ActivityEditAction extends SimpleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        long L = getIntent().getLongExtra(FragmentEditAction.EXTRA_ACTION_ID, 0);
        return FragmentEditAction.newInstance(L);
    }
}
