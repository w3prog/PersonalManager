package com.w3prog.personalmanager.Activity;

import android.app.Fragment;

import com.w3prog.personalmanager.Fragment.FragmentEditTask;

public class ActivityEditTask extends SimpleFragmentActivity {
    @Override
    protected Fragment getFragment() {

        long L = getIntent().getLongExtra(FragmentEditTask.EXTRA_ACTION_ID, 0);
        return FragmentEditTask.newInstance(L);
    }
}
