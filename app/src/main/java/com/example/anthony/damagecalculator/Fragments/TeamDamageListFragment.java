package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Adapters.MonsterDamageListAdapter;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.TextWatcher.MyTextWatcher;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamDamageListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamDamageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDamageListFragment extends Fragment {
    public static final String TAG = MonsterPageFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private ListView monsterListView;
    private EditText additionalComboValue;
    private MonsterDamageListAdapter monsterListAdapter;
    private Enemy enemy;
    private Team team;
    private Toast toast;
    private boolean hasEnemy, hasAbsorb, hasReduction, hasDamageThreshold;
    private ArrayList<OrbMatch> orbMatches;
    private ArrayList<Monster> monsterList;
    private int additionalCombos, additionalCombosFragment, totalCombos = 0, totalDamage = 0, temp = 0;
    private TextView monsterListToggle, enemyHP, enemyHPValue, enemyHPPercent, enemyHPPercentValue, totalDamageValue, totalComboValue, hpRecoveredValue, targetReduction, targetAbsorb;
    private RadioGroup reductionRadioGroup;
    private CheckBox redOrbReduction, blueOrbReduction, greenOrbReduction, lightOrbReduction, darkOrbReduction;
    private RadioGroup absorbRadioGroup;
    private Button recalculateButton;
    private DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamDamageListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamDamageListFragment newInstance(String param1, String param2) {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static TeamDamageListFragment newInstance(boolean hasEnemy, ArrayList<Monster> monsterList, ArrayList<OrbMatch> orbMatches, int additionalCombos, Team team, Enemy enemy, boolean hasAbsorb, boolean hasReduction, boolean hasDamageThreshold) {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putBoolean("hasEnemy", hasEnemy);
        args.putParcelableArrayList("monsterList", monsterList);
        args.putParcelableArrayList("orbMatches", orbMatches);
        args.putInt("additionalCombos", additionalCombos);
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        args.putBoolean("hasAbsorb", hasAbsorb);
        args.putBoolean("hasReduction", hasReduction);
        args.putBoolean("hasDamageThreshold", hasDamageThreshold);
        fragment.setArguments(args);
        return fragment;
    }

    public static TeamDamageListFragment newInstance(boolean hasEnemy, ArrayList<Monster> monsterList, ArrayList<OrbMatch> orbMatches, int additionalCombos, Team team) {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putBoolean("hasEnemy", hasEnemy);
        args.putParcelableArrayList("monsterList", monsterList);
        args.putParcelableArrayList("orbMatches", orbMatches);
        args.putInt("additionalCombos", additionalCombos);
        args.putParcelable("team", team);
        fragment.setArguments(args);
        return fragment;
    }

    public TeamDamageListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_team_damage_list, container, false);
        monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
        additionalComboValue = (EditText) rootView.findViewById(R.id.additionalComboValue);
        monsterListToggle = (TextView) rootView.findViewById(R.id.monsterListToggle);
        enemyHP = (TextView) rootView.findViewById(R.id.enemyHP);
        enemyHPValue = (TextView) rootView.findViewById(R.id.enemyHPValue);
        enemyHPPercent = (TextView) rootView.findViewById(R.id.enemyHPPercent);
        enemyHPPercentValue = (TextView) rootView.findViewById(R.id.enemyHPPercentValue);
        totalDamageValue = (TextView) rootView.findViewById(R.id.totalDamageValue);
        totalComboValue = (TextView) rootView.findViewById(R.id.totalComboValue);
        hpRecoveredValue = (TextView) rootView.findViewById(R.id.hpRecoveredValue);
        targetReduction = (TextView) rootView.findViewById(R.id.targetReduction);
        targetAbsorb = (TextView) rootView.findViewById(R.id.elementAbsorb);
        recalculateButton = (Button) rootView.findViewById(R.id.recalculateButton);
        reductionRadioGroup = (RadioGroup) rootView.findViewById(R.id.reductionOrbRadioGroup);
        absorbRadioGroup = (RadioGroup) rootView.findViewById(R.id.absorbOrbRadioGroup);
        redOrbReduction = (CheckBox) rootView.findViewById(R.id.redOrbReduction);
        blueOrbReduction = (CheckBox) rootView.findViewById(R.id.blueOrbReduction);
        greenOrbReduction = (CheckBox) rootView.findViewById(R.id.greenOrbReduction);
        darkOrbReduction = (CheckBox) rootView.findViewById(R.id.darkOrbReduction);
        lightOrbReduction = (CheckBox) rootView.findViewById(R.id.lightOrbReduction);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            hasEnemy = getArguments().getBoolean("hasEnemy");
            monsterList = getArguments().getParcelableArrayList("monsterList");
            orbMatches = getArguments().getParcelableArrayList("orbMatches");
            additionalCombos = getArguments().getInt("additionalCombos");
            team = getArguments().getParcelable("team");
            enemy = getArguments().getParcelable("enemy");
            hasAbsorb = getArguments().getBoolean("hasAbsorb");
            hasReduction = getArguments().getBoolean("hasReduction");
            hasDamageThreshold = getArguments().getBoolean("hasDamageThreshold");
        }
        if(hasEnemy){
            temp = enemy.getCurrentHp();
        }
        totalCombos = additionalCombos + orbMatches.size();
        updateTextView();
        setReductionOrbs();
        Log.d("totalCombos", String.valueOf(totalCombos));
        monsterListAdapter = new MonsterDamageListAdapter(getActivity(), R.layout.monster_damage_row, monsterList, hasEnemy, orbMatches, enemy, totalCombos, team, hasAbsorb, hasReduction, hasDamageThreshold, totalDamage);
        monsterListView.setAdapter(monsterListAdapter);
        monsterListToggle.setOnClickListener(monsterListToggleOnClickListener);
        additionalComboValue.addTextChangedListener(additionalComboTextWatcher);
        additionalComboValue.setOnFocusChangeListener(editTextOnFocusChange);
        monsterListView.setOnItemClickListener(bindMonsterOnClickListener);
        recalculateButton.setOnClickListener(recalculateButtonOnClickListener);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private View.OnClickListener monsterListToggleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monsterListView.getVisibility() == View.GONE) {
                monsterListView.setVisibility(View.VISIBLE);
                monsterListToggle.setText("Hide Monster Damage Breakdown");
            } else if (monsterListView.getVisibility() == View.VISIBLE) {
                monsterListView.setVisibility(View.GONE);
                monsterListToggle.setText("Show Monster Damage Breakdown");
            }
        }
    };

    public void updateTextView() {
        totalDamage = 0;
        if (!hasEnemy) {
            enemyHP.setVisibility(View.GONE);
            enemyHPValue.setVisibility(View.GONE);
            enemyHPPercent.setVisibility(View.GONE);
            enemyHPPercentValue.setVisibility(View.GONE);
            reductionRadioGroup.setVisibility(View.GONE);
            targetReduction.setVisibility(View.GONE);
            targetAbsorb.setVisibility(View.GONE);
            absorbRadioGroup.setVisibility(View.GONE);
            for (int i = 0; i < monsterList.size(); i++) {
                if (monsterList.get(i).isBound()) {
                } else {
                    totalDamage += monsterList.get(i).getElement1Damage(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), totalCombos);
                    totalDamage += monsterList.get(i).getElement2Damage(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement2()), totalCombos);
                }
            }
        } else {
            enemy.setCurrentHp(temp);
            if (hasDamageThreshold) {
                for (int i = 0; i < monsterList.size(); i++) {
                    if (monsterList.get(i).isBound()) {
                    } else {
                        totalDamage += monsterList.get(i).getElement1DamageThreshold(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        if (totalDamage < 0) {
                            totalDamage = 0;
                        }
                        if (monsterList.get(i).getElement1DamageThreshold(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                            totalDamage = enemy.getCurrentHp() + monsterList.get(i).getElement1DamageThreshold(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        }
                    }
                }
                for (int i = 0; i < monsterList.size(); i++) {
                    if (monsterList.get(i).isBound()) {
                    } else {
                        totalDamage += monsterList.get(i).getElement2DamageThreshold(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement2()), enemy, totalCombos);
                        if (totalDamage < 0) {
                            totalDamage = 0;
                        }
                        if (monsterList.get(i).getElement2DamageThreshold(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                            totalDamage = enemy.getCurrentHp() + monsterList.get(i).getElement2DamageThreshold(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        }
                    }
                }
            } else if (hasAbsorb) {
                for (int i = 0; i < monsterList.size(); i++) {
                    if (monsterList.get(i).isBound()) {
                    } else {
                        totalDamage += monsterList.get(i).getElement1DamageAbsorb(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        if (totalDamage < 0) {
                            totalDamage = 0;
                        }
                        if (monsterList.get(i).getElement1DamageAbsorb(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                            totalDamage = enemy.getCurrentHp() + monsterList.get(i).getElement1DamageAbsorb(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        }
                    }
                }
                for (int i = 0; i < monsterList.size(); i++) {
                    if (monsterList.get(i).isBound()) {
                    } else {
                        totalDamage += monsterList.get(i).getElement2DamageAbsorb(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement2()), enemy, totalCombos);
                        if (totalDamage < 0) {
                            totalDamage = 0;
                        }

                        if (monsterList.get(i).getElement2DamageAbsorb(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                            totalDamage = enemy.getCurrentHp() + monsterList.get(i).getElement2DamageAbsorb(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        }
                    }
                }
            } else if (hasReduction) {
                for (int i = 0; i < monsterList.size(); i++) {
                    if (monsterList.get(i).isBound()) {
                    } else {
                        totalDamage += monsterList.get(i).getElement1DamageReduction(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        totalDamage += monsterList.get(i).getElement2DamageReduction(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement2()), enemy, totalCombos);
                    }
                }
            } else {
                for (int i = 0; i < monsterList.size(); i++) {
                    if (monsterList.get(i).isBound()) {
                    } else {
                        totalDamage += monsterList.get(i).getElement1DamageEnemy(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement1()), enemy, totalCombos);
                        totalDamage += monsterList.get(i).getElement2DamageEnemy(orbMatches, team.getOrbPlusAwakenings(monsterList.get(i).getElement2()), enemy, totalCombos);
                    }
                }
            }
            //Need to set colors of each enemy element stuff
            setTextColors();
            enemy.setCurrentHp(enemy.getCurrentHp() - totalDamage);
            if (enemy.getCurrentHp() < 0) {
                enemy.setCurrentHp(0);
            }
            enemyHPValue.setText(String.valueOf(enemy.getCurrentHp()));
            enemyHPPercentValue.setText(String.valueOf(df.format((double) enemy.getCurrentHp() / enemy.getTargetHp() * 100) + "%"));
        }
        team.setTotalDamage(totalDamage);
        totalDamageValue.setText(String.valueOf(totalDamage));
        hpRecoveredValue.setText(String.valueOf((int) DamageCalculationUtil.hpRecovered(team.getTeamRcv(), orbMatches, totalCombos)));
        totalComboValue.setText(String.valueOf(totalCombos));
    }

    private ListView.OnItemClickListener bindMonsterOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            additionalComboValue.clearFocus();
            if (monsterList.get(position).isBound()) {
                monsterList.get(position).setIsBound(false);
                Log.d("Bound", "monster " + position + " is unbound");
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster unbound", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                monsterList.get(position).setIsBound(true);
                Log.d("Bound", "monster " + position + " is bound");
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster bound", Toast.LENGTH_SHORT);
                toast.show();
            }
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
        }
    };

    private void setTextColors() {
        if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.RED)) {
            enemyHPValue.setTextColor(Color.parseColor("#FF0000"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.BLUE)) {
            enemyHPValue.setTextColor(Color.parseColor("#4444FF"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.GREEN)) {
            enemyHPValue.setTextColor(Color.parseColor("#00CC00"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.LIGHT)) {
            enemyHPValue.setTextColor(Color.parseColor("#F0F000"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.DARK)) {
            enemyHPValue.setTextColor(Color.parseColor("#AA00FF"));
        }
    }

    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {
            if (statToChange == MyTextWatcher.ADDITIONAL_COMBOS) {
                additionalCombosFragment = statValue;
            }
        }
    };

    private MyTextWatcher additionalComboTextWatcher = new MyTextWatcher(MyTextWatcher.ADDITIONAL_COMBOS, changeStats);

    private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
                if ((additionalComboValue.getText().toString().equals(""))) {
                    additionalComboValue.setText("0");
                }
            }
        }
    };

    private Button.OnClickListener recalculateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            totalCombos += additionalCombosFragment;
            Log.d("Total Combo 1", "" + totalCombos);
            if(totalCombos < orbMatches.size()){
                totalCombos = orbMatches.size();
            }
            monsterListAdapter.setCombos(totalCombos);
            Log.d("Total Combo 2", "" + totalCombos);
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
            additionalComboValue.setText("0");
        }
    };

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void setReductionOrbs() {

        if (hasReduction) {
            ArrayList<com.example.anthony.damagecalculator.Data.Color> hi = enemy.getReduction();
            for (int i = 0; i < hi.size(); i++) {
                Log.d("hello2", hi.get(i).toString() + hi.size());
            }
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.RED)) {
                redOrbReduction.setChecked(true);
            }
            Log.d("hello2", "Red: " + enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.RED) + " checked: " + redOrbReduction.isChecked());
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.BLUE)) {
                blueOrbReduction.setChecked(true);
            }
            Log.d("hello2", "Blue: " + enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.BLUE) + " checked: " + blueOrbReduction.isChecked());
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.GREEN)) {
                greenOrbReduction.setChecked(true);
            }
            Log.d("hello2", "GREEN: " + enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.GREEN) + " checked: " + greenOrbReduction.isChecked());
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.DARK)) {
                darkOrbReduction.setChecked(true);
            }
            Log.d("hello2", "DARk: " + enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.DARK) + " checked: " + darkOrbReduction.isChecked());
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.LIGHT)) {
                lightOrbReduction.setChecked(true);
            }
            Log.d("hello2", "Light: " + enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.LIGHT) + " checked: " + lightOrbReduction.isChecked());

        }
    }

    private void setAbsorbOrbs() {
        if (hasAbsorb) {
            absorbRadioGroup.check(R.id.redOrbAbsorb);
            if (enemy.getAbsorb() == com.example.anthony.damagecalculator.Data.Color.RED) {
                absorbRadioGroup.check(R.id.redOrbAbsorb);
            }
        }
    }

}
