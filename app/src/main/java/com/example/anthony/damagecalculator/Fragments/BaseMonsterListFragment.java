package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.anthony.damagecalculator.Adapters.BaseMonsterListAdapter;
import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;


public class BaseMonsterListFragment extends Fragment {
    public static final String TAG = BaseMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private ListView monsterListView;
    private ArrayList<BaseMonster> monsterList;
    private BaseMonsterListAdapter baseMonsterListAdapter;

    public static BaseMonsterListFragment newInstance() {
        BaseMonsterListFragment fragment = new BaseMonsterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public BaseMonsterListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_monster_list, container, false);
        monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
        }
        monsterList = (ArrayList) BaseMonster.getAllMonsters();
        //disableStuff();
        baseMonsterListAdapter = new BaseMonsterListAdapter(getActivity(), R.layout.base_monster_list_row, monsterList);
        monsterListView.setAdapter(baseMonsterListAdapter);
        monsterListView.setOnItemClickListener(monsterListOnClickListener);
    }

    private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Monster newMonster = new Monster(monsterList.get(position).getMonsterId());
            if(Monster.getAllMonsters().size() == 0){
                newMonster.setMonsterId(1);
            }else {
                newMonster.setMonsterId(Monster.getAllMonsters().get(Monster.getAllMonsters().size() - 1).getMonsterId() + 1);
            }
            newMonster.save();
            Team newTeam = new Team(Team.getTeamById(0));
            switch (newTeam.getMonsterOverwrite()) {
                case 0:
                    newTeam.setLead(newMonster);
                    break;
                case 1:
                    newTeam.setSub1(newMonster);
                    break;
                case 2:
                    newTeam.setSub2(newMonster);
                    break;
                case 3:
                    newTeam.setSub3(newMonster);
                    break;
                case 4:
                    newTeam.setSub4(newMonster);
                    break;
                case 5:
                    newTeam.setHelper(newMonster);
                    break;
            }
            newTeam.save();
            Log.d("Base Monster Log", "Team is: " + newTeam.getMonsters());
            Log.d("Base Monster Log", "Sub 4 Level is: " + newTeam.getSub4().getCurrentLevel());
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    private void disableStuff(){
        for (int i = 0; i < monsterList.size(); i++){
            if(monsterList.get(i).getMonsterId() == Team.getTeamById(0).getLead().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub1().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub2().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub3().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub4().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getHelper().getMonsterId()){
                monsterList.remove(i);
            }
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
