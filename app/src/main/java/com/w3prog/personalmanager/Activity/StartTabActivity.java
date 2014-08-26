package com.w3prog.personalmanager.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.w3prog.personalmanager.Fragment.FragmentReport;
import com.w3prog.personalmanager.Fragment.FragmentReportOfDate;
import com.w3prog.personalmanager.Fragment.FragmentTotalReport;
import com.w3prog.personalmanager.Fragment.FragmentTotalReportOfDate;
import com.w3prog.personalmanager.Fragment.List.FragmentListAction;
import com.w3prog.personalmanager.Fragment.List.FragmentListGroup;
import com.w3prog.personalmanager.Fragment.List.FragmentListPerson;
import com.w3prog.personalmanager.Fragment.List.FragmentListPersonInAction;
import com.w3prog.personalmanager.Fragment.List.FragmentListTask;
import com.w3prog.personalmanager.NavigationDrawerFragment;
import com.w3prog.personalmanager.R;

public class StartTabActivity extends Activity implements
        //ActionBar.TabListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    FragmentManager fragmentManager = getFragmentManager();
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_activity);
        setContentView(R.layout.activity_my);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, PlaceholderFragment
                        .newInstance(position + 1))
                .commit();
    }

    protected void openStartPanelFragment() {
        mTitle = "Стартовое меню";
        Fragment fragment = new StartPanelFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openFragmentListPerson() {
        mTitle = getString(R.string.title_section1);
        Fragment fragment = new FragmentListPerson();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openFragmentListGroup() {
        mTitle = getString(R.string.title_section2);
        Fragment fragment = new FragmentListGroup();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openFragmentListAction() {
        mTitle = getString(R.string.title_section3);
        Fragment fragment = new FragmentListAction();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openFragmentListTask() {
        mTitle = getString(R.string.title_section4);
        Fragment fragment = new FragmentListTask();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openFragmentPersonInAction() {
        Fragment fragment = new FragmentListPersonInAction();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openPersonsReportInTask() {
        Fragment fragment = new FragmentReport();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openPersonsReportInDate() {
        Fragment fragment = new FragmentReportOfDate();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openTotalReport(){
        Fragment fragment = new FragmentTotalReport();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }

    protected void openTotalReportofDate(){
        Fragment fragment = new FragmentTotalReportOfDate();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentActivity, fragment)
                .commit();
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                openStartPanelFragment();
                break;
            case 2:
                mTitle = "Провести мероприятие";
                openFragmentPersonInAction();
                break;
            case 3:
                mTitle = "Ввести задачу";
                //todo доделать
                break;
            case 4:
                openFragmentListPerson();
                break;
            case 5:
                openFragmentListGroup();
                break;
            case 6:
                openFragmentListAction();
                break;
            case 7:
                openFragmentListTask();
                break;
            case 8:
                mTitle = "Личный отчет по задаче";
                openPersonsReportInTask();
                break;
            case 9:
                mTitle = "Личный отчет по времени";
                openPersonsReportInDate();
                break;
            case 10:
                mTitle = "Полный отчет по задаче";
                openTotalReport();
                break;
            case 11:
                mTitle = "Полный отчет по времени";
                openTotalReportofDate();
                break;

        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.grid_start, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((StartTabActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public class StartPanelFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        LinearLayout linearLayoutHoldAction;
        LinearLayout linearLayoutDesignateTask;
        LinearLayout linearLayoutListContacts;
        LinearLayout linearLayoutListGroups;
        LinearLayout linearLayoutListAction;
        LinearLayout linearLayoutListTask;
        LinearLayout linearLayoutPersonReportTask;
        LinearLayout linearLayoutPersonReportDate;
        LinearLayout linearLayoutGeneralReportTask;
        LinearLayout linearLayoutGeneralReportDate;

        View.OnClickListener onClickListenerHoldAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragmentPersonInAction();
            }
        };

        View.OnClickListener onClickListenerDesignateTask = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        View.OnClickListener onClickListenerListContacts = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragmentListPerson();
            }
        };

        View.OnClickListener onClickListenerListGroups = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragmentListGroup();
            }
        };

        View.OnClickListener onClickListenerListAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragmentListAction();
            }
        };

        View.OnClickListener onClickListenerListTask = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragmentListTask();
            }
        };

        View.OnClickListener onClickListenerPersonReportTask = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonsReportInTask();
            }
        };

        View.OnClickListener onClickListenerPersonReportDate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonsReportInDate();
            }
        };

        View.OnClickListener onClickListenerGeneralReportTask = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTotalReport();
            }
        };

        View.OnClickListener onClickListenerGeneralReportDate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTotalReportofDate();
            }
        };


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.grid_start, container, false);
            linearLayoutHoldAction = (LinearLayout) view.findViewById(R.id.holdAction);
            linearLayoutDesignateTask = (LinearLayout) view.findViewById(R.id.designateTask);
            linearLayoutListContacts = (LinearLayout) view.findViewById(R.id.listContacts);
            linearLayoutListGroups = (LinearLayout) view.findViewById(R.id.listGroups);
            linearLayoutListAction = (LinearLayout) view.findViewById(R.id.listActions);
            linearLayoutListTask = (LinearLayout) view.findViewById(R.id.listTask);
            linearLayoutPersonReportTask = (LinearLayout) view.findViewById(R.id.personReportTask);
            linearLayoutPersonReportDate = (LinearLayout) view.findViewById(R.id.personReportDate);
            linearLayoutGeneralReportTask = (LinearLayout) view.findViewById(R.id.generalReportTask);
            linearLayoutGeneralReportDate = (LinearLayout) view.findViewById(R.id.generalReportDate);
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            linearLayoutHoldAction.setOnClickListener(onClickListenerHoldAction);
            linearLayoutDesignateTask.setOnClickListener(onClickListenerDesignateTask);
            linearLayoutListContacts.setOnClickListener(onClickListenerListContacts);
            linearLayoutListGroups.setOnClickListener(onClickListenerListGroups);
            linearLayoutListAction.setOnClickListener(onClickListenerListAction);
            linearLayoutListTask.setOnClickListener(onClickListenerListTask);
            linearLayoutPersonReportTask.setOnClickListener(onClickListenerPersonReportTask);
            linearLayoutPersonReportDate.setOnClickListener(onClickListenerPersonReportDate);
            linearLayoutGeneralReportTask.setOnClickListener(onClickListenerGeneralReportTask);
            linearLayoutGeneralReportDate.setOnClickListener(onClickListenerGeneralReportDate);
        }

    }
}
