package com.w3prog.personalmanager.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.w3prog.personalmanager.Fragment.FragmentListAction;
import com.w3prog.personalmanager.Fragment.FragmentListPerson;
import com.w3prog.personalmanager.Fragment.FragmentListTask;
import com.w3prog.personalmanager.R;

public class StartTabActivity extends Activity implements ActionBar.TabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        ActionBar bar = getActionBar();

        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = bar.newTab();
        tab.setTabListener(this);
        tab.setText(getString(R.string.Contacts));
        bar.addTab(tab);

        tab = bar.newTab();
        tab.setTabListener(this);
        tab.setText(getString(R.string.Actions));
        bar.addTab(tab);

        tab = bar.newTab();
        tab.setTabListener(this);
        tab.setText(getString(R.string.Tasks));
        bar.addTab(tab);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentActivity);
        if (fragment != null)
            ft.remove(fragment);
        switch (tab.getPosition()){
            case 0:
                fragment = new FragmentListPerson();
                getActionBar().setTitle(R.string.Contacts);
                ft.add(R.id.fragmentActivity, fragment);
                break;
            case 1:
                fragment = new FragmentListAction();
                getActionBar().setTitle(R.string.Actions);
                ft.replace(R.id.fragmentActivity, fragment);
                break;
            case 2:
                fragment = new FragmentListTask();
                ft.add(R.id.fragmentActivity,fragment);
                break;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
