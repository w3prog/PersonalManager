package com.w3prog.personalmanager.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.w3prog.personalmanager.R;

public abstract class SimpleFragmentActivity extends Activity {

    protected abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentActivity);
        if (fragment == null) {
            fragment = getFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentActivity, fragment)
                    .commit();
        }
    }
}
