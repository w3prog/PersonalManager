package com.w3prog.personalmanager.Fragment.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.Activity.ActivityEditAction;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.Fragment.Edit.FragmentEditAction;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentListAction extends ListFragment {
    private static final String TAG = "FragmentListAction";
    private ArrayList<Action> actionsArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionsArrayList = DataBase.get(getActivity()).getActions();
        setHasOptionsMenu(true);
        ActionAdapter actionAdapter = new ActionAdapter(actionsArrayList);
        setListAdapter(actionAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Action action = ((ActionAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), ActivityEditAction.class);
        i.putExtra(FragmentEditAction.EXTRA_ACTION_ID, action.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        actionsArrayList = DataBase.get(getActivity()).getActions();
        ActionAdapter actionAdapter = new ActionAdapter(actionsArrayList);
        setListAdapter(actionAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.footer_list_action, null);
        Button buttonFooter = (Button) view.findViewById(R.id.FooterListAction);
        buttonFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l = DataBase.get(getActivity()).insertNewAction();
                Intent i = new Intent(getActivity(), ActivityEditAction.class);
                i.putExtra(FragmentEditAction.EXTRA_ACTION_ID, l);
                startActivity(i);
            }
        });
        getListView().addFooterView(view);

        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position,
                                                  long id,
                                                  boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflaterMenu = mode.getMenuInflater();
                inflaterMenu.inflate(R.menu.context_menu_list, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deleteItem:
                        ActionAdapter adapter = (ActionAdapter) getListAdapter();
                        actionsArrayList = DataBase.get(getActivity()).getActions();
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                DataBase.get(getActivity()).deleteAction(
                                        adapter.getItem(i).getId());
                            }
                        }
                        mode.finish();
                        actionsArrayList = DataBase.get(getActivity()).getActions();
                        ActionAdapter actionAdapter = new ActionAdapter(actionsArrayList);
                        setListAdapter(actionAdapter);
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    private class ActionAdapter extends ArrayAdapter<Action> {

        public ActionAdapter(ArrayList<Action> actions) {
            super(getActivity(), 0, actions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }
            Action action = getItem(position);

            TextView textViewName = (TextView) convertView.findViewById(android.R.id.text1);
            textViewName.setText(action.getName());
            return convertView;
        }

    }


}
