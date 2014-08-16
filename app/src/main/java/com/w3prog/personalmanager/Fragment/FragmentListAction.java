package com.w3prog.personalmanager.Fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.personalmanager.Action;
import com.w3prog.personalmanager.Activity.ActivityEditAction;
import com.w3prog.personalmanager.DataBase;
import com.w3prog.personalmanager.R;

import java.util.ArrayList;

public class FragmentListAction extends ListFragment {
    private static final String TAG = "FragmentListAction";
    private ArrayList<Action> actionsArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionsArrayList = DataBase.Get(getActivity()).getActions();
        setHasOptionsMenu(true);
        ActionAdapter actionAdapter = new ActionAdapter(actionsArrayList);
        setListAdapter(actionAdapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Action action = ((ActionAdapter)getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(),ActivityEditAction.class);
        i.putExtra(FragmentEditAction.EXTRA_ACTION_ID,action.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {

        super.onResume();
        actionsArrayList = DataBase.Get(getActivity()).getActions();

        //todo Можно ли это сделать попроще

        ActionAdapter personAdapter = new ActionAdapter(actionsArrayList);
        setListAdapter(personAdapter);
    }

    //todo пока бессмысленно нужно будет определить как лучше сохранять и добавлять данные
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_action, menu);
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_new_groupCrime:
                Event event = new Event();
                Intent intent = getIntent();
                int idGroup = intent.getIntExtra(EXTRA_Id,1);

                Log.d(TAG, "В приложение уже вошли!");
                EventCollection.get(this).addEvent(event);
                Intent i = new Intent(this, EditEventActivity.class);
                i.putExtra(EditEventActivity.EXTRA_ID, event.getId());
                i.putExtra(EditEventActivity.EXTRA_GROUP_ID, idGroup);
                i.putExtra(EditEventActivity.EXTRA_TYPE,true);
                Log.d(TAG,"В приложение уже вошли!");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    private class ActionAdapter extends ArrayAdapter<Action>{

        public ActionAdapter(ArrayList<Action> actions) {
            super(getActivity(), 0,actions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1,null);
            }
            Action action = getItem(position);

            TextView textViewName = (TextView)convertView.findViewById(android.R.id.text1);
            textViewName.setText(action.getName());
            return convertView;
        }

    }


}
