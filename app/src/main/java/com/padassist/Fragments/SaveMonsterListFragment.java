package com.padassist.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.MonsterPagerAdapter;
import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.Data.Element;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Graphics.FastScroller;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.MonsterAlphabeticalComparator;
import com.padassist.Util.MonsterAtkComparator;
import com.padassist.Util.MonsterAwakeningComparator;
import com.padassist.Util.MonsterElement1Comparator;
import com.padassist.Util.MonsterElement2Comparator;
import com.padassist.Util.MonsterFavoriteComparator;
import com.padassist.Util.MonsterHpComparator;
import com.padassist.Util.MonsterLevelComparator;
import com.padassist.Util.MonsterNumberComparator;
import com.padassist.Util.MonsterPlusAtkComparator;
import com.padassist.Util.MonsterPlusComparator;
import com.padassist.Util.MonsterPlusHpComparator;
import com.padassist.Util.MonsterPlusRcvComparator;
import com.padassist.Util.MonsterRarityComparator;
import com.padassist.Util.MonsterRcvComparator;
import com.padassist.Util.MonsterType1Comparator;
import com.padassist.Util.MonsterType2Comparator;
import com.padassist.Util.MonsterType3Comparator;
import com.padassist.Util.SaveMonsterListUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SaveMonsterListFragment extends SaveMonsterListUtil {
    public static final String TAG = SaveMonsterListFragment.class.getSimpleName();
    private Toast toast;
    private boolean replaceAll;
    private long replaceMonsterId;
    private Monster monsterZero = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;

    private FastScroller fastScroller;

    public static SaveMonsterListFragment newInstance(boolean replaceAll, long replaceMonsterId) {
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        fragment.setArguments(args);
        return fragment;
    }

    public SaveMonsterListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
        }

        saveMonsterListRecycler = new SaveMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener, monsterListOnLongClickListener, deleteOnClickListener);
        monsterListView.setAdapter(saveMonsterListRecycler);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreatedSpecific() {
//        if (Singleton.getInstance().getMonsterOverwrite() == 5) {
//            monsterListAll = (ArrayList) Monster.getAllHelperMonsters();
//            monsterListAll.add(monsterZero);
//        } else {
//            monsterListAll = (ArrayList) Monster.getAllSavedMonsters();
//        }
//        Log.d("SaveMonsterList", "MonsterList is: " + monsterList);
    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
//            Team newTeam = new Team(Team.getTeamById(0));
//            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                if (replaceAll) {
//                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
//                    Team replaceTeam;
//                    for (int i = 0; i < teamList.size(); i++) {
//                        replaceTeam = teamList.get(i);
//                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
//                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
//                                replaceTeam.setMonsters(j, saveMonsterListRecycler.getItem(position));
//                            }
//                        }
//                        replaceTeam.save();
//                    }
//                } else {
//                    switch (Singleton.getInstance().getMonsterOverwrite()) {
//                        case 0:
//                            newTeam.setLead(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 1:
//                            newTeam.setSub1(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 2:
//                            newTeam.setSub2(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 3:
//                            newTeam.setSub3(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 4:
//                            newTeam.setSub4(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 5:
//                            newTeam.setHelper(saveMonsterListRecycler.getItem(position));
//                            break;
//                    }
//                    newTeam.save();
//                }
//
////                Intent intent = new Intent();
////                Bundle bundle = new Bundle();
////                bundle.putParcelable("monster", saveMonsterListRecycler.getItem(position));
////                bundle.putLong("monsterId", saveMonsterListRecycler.getItem(position).getMonsterId());
////                intent.putExtras(bundle);
//
////                intent.putExtra("monster", saveMonsterListRecycler.getItem(position));
////                intent.putExtra("monsterId", saveMonsterListRecycler.getItem(position).getMonsterId());
//
////                Log.d("SaveMonsterList", "monster is: " + intent.getParcelableExtra("monster") + " extra is: " + intent.getExtras());
////                getActivity().setResult(Activity.RESULT_OK, intent);
////                getActivity().finish();
//
//                getActivity().getSupportFragmentManager().popBackStack(MonsterListFragment.TAG, 0);
//                ((MainActivity)getActivity()).switchFragment(MonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position), Singleton.getInstance().getMonsterOverwrite()), MonsterPageFragment.TAG, "good");
//            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag(R.string.index);
//            Team newTeam = new Team(Team.getTeamById(0));
//            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                if (replaceAll) {
//                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
//                    Team replaceTeam;
//                    for (int i = 0; i < teamList.size(); i++) {
//                        replaceTeam = teamList.get(i);
//                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
//                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
//                                replaceTeam.setMonsters(j, saveMonsterListRecycler.getItem(position));
//                            }
//                        }
//                        replaceTeam.save();
//                    }
//                } else {
//
//                    switch (Singleton.getInstance().getMonsterOverwrite()) {
//                        case 0:
//                            newTeam.setLead(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 1:
//                            newTeam.setSub1(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 2:
//                            newTeam.setSub2(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 3:
//                            newTeam.setSub3(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 4:
//                            newTeam.setSub4(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 5:
//                            newTeam.setHelper(saveMonsterListRecycler.getItem(position));
//                            break;
//                    }
//                    newTeam.save();
//                }
//                getActivity().getSupportFragmentManager().popBackStack(MonsterListFragment.TAG, 0);
//                ((MainActivity)getActivity()).switchFragment(MonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position), Singleton.getInstance().getMonsterOverwrite()), MonsterPageFragment.TAG, "good");
//            }
            return true;
        }
    };


    private View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            if (deleteConfirmationDialog == null) {
                deleteConfirmationDialog = DeleteMonsterConfirmationDialogFragment.newInstance(deleteMonster, position);
            }
            deleteConfirmationDialog.show(getChildFragmentManager(), position, "Delete Confirmation");
        }
    };

    protected DeleteMonsterConfirmationDialogFragment.ResetLayout deleteMonster = new DeleteMonsterConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout(int position) {
//            ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
//            Team newTeam;
//            for (int i = 0; i < teamList.size(); i++) {
//                newTeam = teamList.get(i);
//                for (int j = 0; j < newTeam.getMonsters().size(); j++) {
//                    if (newTeam.getMonsters().get(j).getMonsterId() == saveMonsterListRecycler.getItem(position).getMonsterId()) {
//                        newTeam.setMonsters(j, Monster.getMonsterId(0));
//                    }
//                }
//                newTeam.save();
//            }
//            Monster.getMonsterId(saveMonsterListRecycler.getItem(position).getMonsterId()).delete();
//            for(int i = 0; i < monsterListAll.size(); i++){
//                if(monsterListAll.get(i).getMonsterId() == saveMonsterListRecycler.getItem(position).getMonsterId()){
//                    monsterListAll.remove(i);
//                }
//            }
//            monsterList.remove(position);
//            saveMonsterListRecycler.notifyItemRemoved(position);
//            saveMonsterListRecycler.notifyDataSetChanged(monsterList);
//            saveMonsterListRecycler.setExpandedPosition(-1);
//            emptyCheck();
        }
    };
}
